package calculator;

public class div
{
    public static double div(int a,int b)
    {
        if (b==0)
        {
            throw new ArithmeticException("cannot divide zero");
        }
        return (double)a/b;
    }
}
