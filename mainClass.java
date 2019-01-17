package temp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConvertToZip {


	public static void main(String [] a)  {
		String[] data = {
			"Line 1",
			"Line 2 2",
			"Line 3 3 3",
			"Line 4 4 4 4",
			"Line 5 5 5 5 5"
		};
		try(FileSystem zip = openZip(Paths.get("myData.zip"))){
			copyToZip(zip);
			writeToZipFile1(zip,data);
			writeToZipFile2(zip,data);
		}catch(Exception e) {
			
			System.out.println(e.getClass().getSimpleName() +" : "+e.getMessage());
		}
	}





	private static FileSystem openZip(Path zipPath) throws URISyntaxException,IOException {		
		Map<String, String> providerProps = new HashMap<>();
		providerProps.put("create", "ture");
		
		URI url =  new URI("jar:file",zipPath.toUri().getPath(),null);
		
		FileSystem zipFs = FileSystems.newFileSystem(url, providerProps);
		
		return zipFs;
	}
	private static void copyToZip(FileSystem zip) throws IOException {
		
		Path sourceFile = Paths.get("file1.txt");
		Path destinationFile =  zip.getPath("/file1Copied.txt");
		Files.copy(sourceFile, destinationFile,StandardCopyOption.REPLACE_EXISTING);
	}
	
	private static void writeToZipFile2(FileSystem zip, String[] data) throws IOException{
		try(BufferedWriter writer = Files.newBufferedWriter(zip.getPath("/newFile1.txt"))){
			for(String d:data) {
				writer.write(d);
				writer.newLine();
			}
		}
	}


	private static void writeToZipFile1(FileSystem zip, String[] data) throws IOException{
		Files.write(zip.getPath("./newFile2.txt"), Arrays.asList(data),
				Charset.defaultCharset(), 
				StandardOpenOption.CREATE);
	}

		
}
