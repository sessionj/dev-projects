package egovframework.common.util;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @FileName  : AuthenticationKeyGeneration.java
 * @Project   : kr.co.ds.api
 * @Date      : 2022. 12. 6.
 * @작성자     : shadow

 * @변경이력 		: 
 * @프로그램 설명 : 6자리 인증번호를 생성
 */
public class AuthenticationKeyGeneration {

	/**
     * 6자리 인증키 생성, int 반환
     * @return
     */
    public static int generateAuthNo1() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
    
    /**
     * 6자리 인증키 생성, int 반환
     * @return
     */
    public static int generateAuthNo2() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        return generator.nextInt(1000000) % 1000000;
    }
    
    /**
     * apache commons 이용, int 반환
     * @return
     */
    public static int generateAuthNo3() {
        return RandomUtils.nextInt(100000, 1000000);
    }
    
    /**
     * apache commons 이용, String 반환
     * @return
     */
    public static String generateAuthNo4() {
        return RandomStringUtils.randomNumeric(6);
    }
    
    public static void main(String[] args) {
        System.out.println(generateAuthNo1());
        System.out.println(generateAuthNo2());
        System.out.println(generateAuthNo3());
        System.out.println(generateAuthNo4());
    }
}
