package service;

public class GenerateOTP {
    public static String getOTP(){
        String otp = String.format("%06d", (int) (Math.random() * 1000000));
        return otp;
    }
}
