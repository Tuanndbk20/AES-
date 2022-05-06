package AES_CCM_GCM;

public interface GCMExponentiator
{
    void init(byte[] x);
    void exponentiateX(long pow, byte[] output);
}
