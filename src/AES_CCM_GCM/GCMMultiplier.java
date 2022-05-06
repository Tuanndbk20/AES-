package AES_CCM_GCM;

public interface GCMMultiplier
{
    void init(byte[] H);
    void multiplyH(byte[] x);
}
