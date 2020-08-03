package core.staticfunctions;

public abstract class TextEvent 
{
	public static void outCongratulationJoker()
	{
		System.out.println("Gratulacje - Joker. Masz mozliwosc uzyskania maksymalnej liczby punktow");
		System.out.println("za ktoras z niewykorzystanych kategorii dolnej czesci tabelki.");
		System.out.println("Jako bonus dostajesz 100pkt.");
	}
	
	public static String outTypeNameOfPlayerNumber(int a)
	{
		return ("Name of player #" + a);
	}
	
	public static String outNextPlayersTurn(String name)
	{
		return (name + "'s turn");
	}
}
