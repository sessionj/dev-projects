package egovframework.common.util;

public class TestJavaSource {

	public static void main(String[] args) {

		System.out.println(StringCommonLibray.CODE1.getMsg()  );
		System.out.println(StringCommonLibray.CODE1.getCode()  );
		
		
		System.out.println("02".compareTo(StringCommonLibray.CODE1.getCode()));
		
	}
}


