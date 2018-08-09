package com.chnghx.web.lombok.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;

import com.chnghx.web.lombok.entity.Persion;

import lombok.Cleanup;
import lombok.NonNull;

public class TestLombok {

	public static void main(String[] args) {
		
		Persion p = Persion.builder().id(1).age(18).build();
		System.out.println(p.toString());
		p.hashCode();
		
		Persion p2 = Persion.builder().id(2).age(18).build();
		p2.setAge(18);
		p2.setHeight(171);
		System.out.println(p2.toString());
		
//		Assert.assertEquals("成功", p.getAge(), p2.getAge());
		
		
		try {
			testCleanUp(p.getAge());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testCleanUp(@NonNull Integer age) throws FileNotFoundException, IOException {
		 @Cleanup InputStream in = new FileInputStream("F://a.txt");
		 @Cleanup OutputStream out = new FileOutputStream("F://b.txt");
		
		 byte[] b = new byte[1024];
		 while (true) {
		 int r = in.read(b);
		 if (r == -1)
		 break;
		 out.write(b, 0, r);
		 }

	}

}
