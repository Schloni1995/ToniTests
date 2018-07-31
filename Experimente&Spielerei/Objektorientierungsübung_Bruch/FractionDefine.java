package Objektorientierungsübung_Bruch;

public class FractionDefine
{
	static int anz = 0; // Muss nicht extra initialisiert werden
	int zaehler, nenner, fak;

	public FractionDefine()
	{

	}

	void ausgabe()
	{
		if ((zaehler == 0) && (nenner == 1))
		{
			System.out.println("Standardbruch = " + zaehler + "/" + nenner);
			System.out.println("--->Also " + zaehler);
		}

		else
			System.out.println("Bruch = " + zaehler + "/" + nenner);
	}
}