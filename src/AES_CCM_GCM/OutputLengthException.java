package AES_CCM_GCM;

//import org.bouncycastle.crypto.DataLengthException;

public class OutputLengthException
extends DataLengthException
{
public OutputLengthException(String msg)
{
    super(msg);
}
}
