package yahtzee;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Yatzee yetzee = new Yatzee();
	}

}

class Yatzee
{
	Speler speler;
	Dobbelsteen[] dobbelstenen = new Dobbelsteen[5];
	ScoreFormulier scoreFormulier = new ScoreFormulier();
	Random random = new Random();
	
	Yatzee()
	{
		int j = 1;
		for(int i = 0; i < dobbelstenen.length; i++)
		{
			dobbelstenen[i] = new Dobbelsteen(j);
			j++;
		}
		System.out.println("Welkom bij mijn yahtzee, kies a voor uw eerste worp en q om het spel te beëindigen");
		boolean doorspelen = true;
		while(doorspelen)
		{
			String input = toonMenu();
			if(input.equals("a"))
			{
				for(Dobbelsteen steen: dobbelstenen)
				{
					if(!steen.vastgezet)
					{
						gooiDobbelsteen(steen);
						System.out.println("Dobbelsteen " + steen.dobbelsteennummer + " heeft het volgende aantal ogen gegooid: " +steen.huidigCijfer);
					}
					else
					{
						System.out.println("Dobbelsteen " + steen.dobbelsteennummer + " die u heeft vastgezet heeft het volgende aantal ogen: " +steen.huidigCijfer);
					}
				}
				System.out.println("Wanneer u nog een keer wilt gooien typ o");
				System.out.println("Wanneer u een dobbelsteen wilt vastzetten typ v");
				System.out.println("wanneer u met de huidige dobbelstenen een score wilt vastzetten typ s");
			}
			else if(input.equals("q"))
			{
				doorspelen = false;
			}
			else if(input.equals("o"))
			{
				System.out.println("Typ de steennummers waarmee u opnieuw wilt gooiën (bijv. 1, 4, 5)");
			}
			else if(input.equals("v"))
			{
				System.out.println("Wat is het nummer van de dobbelsteen die u wilt vastzetten?");
			}
			else if(Character.isDigit(input.charAt(0)))
			{
				int nummerD = Character.getNumericValue(input.charAt(0));
				if(nummerD < 6 && nummerD > 0)
				{
					int nummerI = nummerD - 1;
					dobbelstenen[nummerI].vastgezet = true;
					System.out.println("Kies nog een dobbelsteen(nummer) of typ a om met de nog overgebleven dobbelstenen te gooiën");
				}
				else
				{
					System.out.println("Dit is geen geldige input: kies opnieuw een dobbelsteen (cijfer 1 t/ 5)");
				}
			}
			else if(input.equals("s"))
			{
				System.out.println("Typ y gevolgd door (een spatie en) een van de volgende opties:");
				System.out.println("- een - twee - drie - vier - vijf - zes (voor het aantal keer dezelfde ogen van een bepaald cijfer");
				System.out.println("driedezelfde");
				System.out.println("carré (vier dezelfde dobbelstenen + een andere)");
				System.out.println("full house (drie dezelfde en twee dezelfde)");
				System.out.println("kleinestraat (reeks van 4 opeenvolgende getallen");
				System.out.println("grotestraat (reeks van 5 opeenvolgende getallen");
				System.out.println("yatzee (allemaal verschillende getallen");
			}
			else if(input.startsWith("y"))
			{
				checkWorp(dobbelstenen, input);
			}
		}
		System.out.println("Bedankt voor het spelen");

	}
	static String toonMenu()
	{
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	void gooiDobbelsteen(Dobbelsteen steen)
	{
		steen.huidigCijfer = random.nextInt(6) + 1;
	}
	void checkWorp(Dobbelsteen[] stenen, String input)
	{
		System.out.println(input);
		String yat = input.substring(2);
		System.out.println(yat);
		for(Dobbelsteen steen: stenen)
		{
			System.out.print(steen.huidigCijfer);
		}
		System.out.println("nieuwe worp");
	}

}

class Speler
{
	String naam;
	Speler(String spelernaam)
	{
		naam = spelernaam;
	}
}

class Dobbelsteen
{
	int dobbelsteennummer;
	int[] ogen = new int[6];
	int huidigCijfer = 0;
	boolean vastgezet;
	Dobbelsteen(int nummer)
	{
		dobbelsteennummer = nummer;
		int cijfer = 1;
		for(int zijde: ogen)
		{
			zijde = cijfer;
			//System.out.println(zijde);
			cijfer++;
		}
	}
}

class ScoreFormulier
{

}