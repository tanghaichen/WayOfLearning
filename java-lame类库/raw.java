package com.cobona.vici.guangji.fileupload.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Mp3Util {

	public static int SAMPLE_RATE = 16000;
	
	private void writeInt(final DataOutputStream output, final int value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
        output.write(value >> 16);
        output.write(value >> 24);
    }

    private void writeShort(final DataOutputStream output, final short value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
    }

    private void writeString(final DataOutputStream output, final String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            output.write(value.charAt(i));
        }
    }
	
    /* Converting RAW format To WAV Format*/
    public void rawToWave(final File rawFile, final File waveFile) throws IOException {

        byte[] rawData = new byte[(int) rawFile.length()];
        DataInputStream input = null;
        try {
            input = new DataInputStream(new FileInputStream(rawFile));
            input.read(rawData);
        } finally {
            if (input != null) {
                input.close();
            }
        }
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(new FileOutputStream(waveFile));
            // WAVE header
            writeString(output, "RIFF"); // chunk id
            writeInt(output, 36 + rawData.length); // chunk size
            writeString(output, "WAVE"); // format
            writeString(output, "fmt "); // subchunk 1 id
            writeInt(output, 16); // subchunk 1 size
            writeShort(output, (short) 1); // audio format (1 = PCM)
            writeShort(output, (short) 1); // number of channels
            writeInt(output, SAMPLE_RATE); // sample rate
            writeInt(output, SAMPLE_RATE * 2); // byte rate
            writeShort(output, (short) 2); // block align
            writeShort(output, (short) 16); // bits per sample
            writeString(output, "data"); // subchunk 2 id
            writeInt(output, rawData.length); // subchunk 2 size
            // Audio data (conversion big endian -> little endian)
            short[] shorts = new short[rawData.length / 2];
            ByteBuffer.wrap(rawData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
            ByteBuffer bytes = ByteBuffer.allocate(shorts.length * 2);
            for (short s : shorts) {
                bytes.putShort(s);
            }
            output.write(bytes.array());
        } finally {
            if (output != null) {
                output.close();
                rawFile.delete();
            }
        }


    }
    
    public static String createBat(String command) throws IOException {  
        String dir = "d:\\lame.bat";  
  
        byte[] b = command.getBytes();  
        File file = new File(dir);  
        if (!file.exists()) {  
            file.createNewFile();  
        }  
        FileOutputStream os = new FileOutputStream(file);  
        os.write(b);  
        os.close();  
  
        return dir;  
  
    }  
    
    
	public static void rawToMp3(String rawPath) {
		String mp3Path = rawPath.replace(".raw", ".mp3");

		try {

			String command = "src/main/java/com/cobona/vici/guangji/fileupload/utils/lame.exe  -r " + rawPath+ " -s 15 -m m " + mp3Path;
			Process process = Runtime.getRuntime().exec(command);

			new Thread() {
				@Override
				public void run() {
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;

					try {
						while ((line = in.readLine()) != null) {
							// System.out.println("output: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line = null;

					try {
						while ((line = err.readLine()) != null) {
							// System.out.println("err: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							
							err.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}.start();

			process.waitFor();//直接调用会导致进程挂起，无法结束
			//所以在执行此步骤之前让主进程执行InputStream和ErrorStream线程可避免此问题
			System.out.println("转换成功");

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
   
 /*
	public static void main(String[] args) {
		raw ra = new raw();
		String rawPath = "F:/360MoveData/Users/96427/Desktop/raw_mp/Voice.raw";
		String mp3Path = rawPath.replace(".raw", ".mp3");
		File rawFile = new File(rawPath);
		File waveFile = new File("C:/Users/admin/Desktop/lame-3.100/Voice.wav");
		mp3Path="F:/360MoveData/Users/96427/Desktop/raw_mp/9.mp3";
		
		try {  
            String command = "F:/360MoveData/Users/96427/Desktop/raw_mp/lame.exe  -r "+rawPath+" -s 15 -m m " + mp3Path;  
//            String cmd = createBat(command);  
            Process process = Runtime.getRuntime().exec(command);
            System.out.println(process.getInputStream());

			new Thread()
			{
				@Override
				public void run()
				{
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream())); 
					String line = null;
					
					try 
					{
						while((line = in.readLine()) != null)
						{
							System.out.println("output: " + line);
						}
					} 
					catch (IOException e) 
					{						
						e.printStackTrace();
					}
					finally
					{
						try 
						{
							in.close();
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}.start();
			
			new Thread()
			{
				@Override
				public void run()
				{
					BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream())); 
					String line = null;
					
					try 
					{
						while((line = err.readLine()) != null)
						{
							//System.out.println("err: " + line);
						}
					} 
					catch (IOException e) 
					{						
						e.printStackTrace();
					}
					finally
					{
						try 
						{
							err.close();
						} 
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
				}

			}.start();
			
            int waitFor = process.waitFor();
            System.out.println(waitFor);
  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
		
//		"lame -r ../Voice.raw -s 15 -m m ./voice.mp3";
//		try {
//			ra.rawToWave(rawFile, waveFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
*/
}
