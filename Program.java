package yahtzee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
	int poging = 0;
	
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
			poging++;
			String input = toonMenu();
			if(input.equals("a"))
			{
				if(poging > 3)
				{
					System.out.println("U heeft drie keer gegooid, u moet een score vastzetten, typ s");
				}
				else
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
				System.out.println("fullhouse (drie dezelfde en twee dezelfde)");
				System.out.println("kleinestraat (reeks van 4 opeenvolgende getallen)");
				System.out.println("grotestraat (reeks van 5 opeenvolgende getallen)");
				System.out.println("yatzee (allemaal verschillende getallen)");
			}
			else if(input.startsWith("y"))
			{
				boolean scorevastgezet = checkWorp(dobbelstenen, input);
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
	boolean checkWorp(Dobbelsteen[] stenen, String input)
	{
		//System.out.println(input);
		String yat = input.substring(2);
		//System.out.println(yat);
		boolean scorevastgezet = false;
		if(yat.equals("yatzee"))
		{
			boolean hetzelfde = true;
			int dyat = stenen[0].huidigCijfer;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer != dyat)
				{
					hetzelfde = false;
				}
			}
			if(hetzelfde)
			{
				if(scoreFormulier.yatzee == -1)
				{
					scoreFormulier.yatzee = 50;
					scorevastgezet = true;
				}
				else
				{
					System.out.println("De score voor yatzee is al ingevuld, kies een andere optie");
				}
			}
			else
			{
				System.out.println("Sorry, u heeft geen yatzee gegooid");
			}
		}
		else if(yat.equals("grotestraat")) 	// vijf opeenvolgende getallen
		{
			int hetzelfde = 0;
			for(int x = 0; x < stenen.length; x++)
			{
				for(int i= 0; i < stenen.length; i++)
				{
					if(stenen[x].dobbelsteennummer != stenen[i].dobbelsteennummer)
					{
						if(stenen[x].huidigCijfer == stenen[i].huidigCijfer)
						{
								hetzelfde++;
						}
						//System.out.println(stenen[x].dobbelsteennummer + ": " + stenen[x].huidigCijfer + " vs " + stenen[i].dobbelsteennummer + ": " + stenen[i].huidigCijfer);
					}
				}
			}
			if(hetzelfde == 0)
			{
				if(scoreFormulier.grotestraat == -1)
				{
					scoreFormulier.grotestraat = 40;
					scorevastgezet = true;
				}
				else
				{
					System.out.println("De score voor grotestraat is al ingevuld, kies een andere optie");
				}
			}
			else
			{
				System.out.println("Sorry, u heeft geen grote straat gegooid");
			}
		}
		else if(yat.equals("kleinestraat")) // een ononderbroken reeks van 4 getallen
		{
			Arrays.sort(stenen, new Comparator<Dobbelsteen>() //sorteren van cijfers in array
			{
				@Override
				public int compare(Dobbelsteen o1, Dobbelsteen o2) {
					// TODO Auto-generated method stub
					if(o1.huidigCijfer > o2.huidigCijfer)
					{
						return -1;
					}
					else if(o1.huidigCijfer == o2.huidigCijfer)
					{
						return 0;
					}
					else
					{
						return 1;
					}
				}
			});
			int tellerk = 5;
			int hetzelfde = 0;
			boolean kstraat = true;
			if(stenen[0].huidigCijfer == 6)
			{
				for(int t = 1; t < stenen.length-1; t++)
				{
					if(stenen[t].huidigCijfer == tellerk)
					{
						tellerk--;
					}
					else if(stenen[t].huidigCijfer == stenen[t-1].huidigCijfer)
					{
						hetzelfde++;
					}
					else
					{
						kstraat = false;
					}
				}
				if(kstraat && hetzelfde <= 1)
				{
					if(scoreFormulier.kleinestraat == -1)
					{
						scoreFormulier.kleinestraat = 30;
						scorevastgezet = true;
					}
					else
					{
						System.out.println("De score voor kleinestraat is al ingevuld, kies een andere optie");
					}
				}
				else
				{
					System.out.println("Sorry u heeft geen kleinestraat gegooid");
				}
			}
			else if(stenen[0].huidigCijfer == 5)
			{
				tellerk = 4;				
				for(int t = 1; t < stenen.length-1; t++)
				{
					if(stenen[t].huidigCijfer == tellerk)
					{
						tellerk--;
					}
					else if(stenen[t].huidigCijfer == stenen[t-1].huidigCijfer)
					{
						hetzelfde++;
					}
					else
					{
						kstraat = false;
					}
				}
				if(kstraat && hetzelfde <= 1)
				{
					if(scoreFormulier.kleinestraat == -1)
					{
						scoreFormulier.kleinestraat = 30;
						scorevastgezet = true;
					}
					else
					{
						System.out.println("De score voor kleinestraat is al ingevuld, kies een andere optie");
					}
				}
				else
				{
					System.out.println("Sorry u heeft geen kleinestraat gegooid");
				}
			}
			else if(stenen[0].huidigCijfer == 4)
			{
				tellerk = 3;				
				for(int t = 1; t < stenen.length-1; t++)
				{
					if(stenen[t].huidigCijfer == tellerk)
					{
						tellerk--;
					}
					else if(stenen[t].huidigCijfer == stenen[t-1].huidigCijfer)
					{
						hetzelfde++;
					}
					else
					{
						kstraat = false;
					}
				}
				if(kstraat && hetzelfde <= 1)
				{
					if(scoreFormulier.kleinestraat == -1)
					{
						scoreFormulier.kleinestraat = 30;
						scorevastgezet = true;
					}
					else
					{
						System.out.println("De score voor kleinestraat is al ingevuld, kies een andere optie");
					}
				}
				else
				{
					System.out.println("Sorry u heeft geen kleinestraat gegooid");
				}
			}
		}
		else if(yat.equals("fullhouse"))
		{
			int[] ogen = {0, 0, 0, 0, 0, 0};
			for(int x = 0; x < stenen.length; x++)
			{
				for(int i= 0; i < stenen.length; i++)
				{
					if(stenen[x].dobbelsteennummer != stenen[i].dobbelsteennummer)
					{
						if(stenen[x].huidigCijfer == stenen[i].huidigCijfer)
						{
							int hcijfera = stenen[x].huidigCijfer - 1;
							ogen[hcijfera] = ogen[hcijfera] + 1;
						}
					}
				}
			}
			boolean tweedezelfde = false;
			boolean driedezelfde = false;
			for(int a = 5; a >= 0; a--)
			{
				if(ogen[a] > 2)
				{
					driedezelfde = true;
				}
				else if(ogen[a] > 1)
				{
					tweedezelfde = true;
				}
			}
			if(tweedezelfde && driedezelfde)
			{
				if(scoreFormulier.fullhouse == -1)
				{
					scoreFormulier.fullhouse = 25;
					scorevastgezet = true;
				}
				else
				{
					System.out.println("De score voor fullhouse is al ingevuld, kies een andere optie");
				}
			}
			else
			{
				System.out.println("Sorry u heeft geen fullhouse gegooid");
			}
		}
		else if(yat.equals("carré"))
		{
			int[] ogen = {0, 0, 0, 0, 0, 0};
			for(int x = 0; x < stenen.length; x++)
			{
				for(int i= 0; i < stenen.length; i++)
				{
					if(stenen[x].dobbelsteennummer != stenen[i].dobbelsteennummer)
					{
						if(stenen[x].huidigCijfer == stenen[i].huidigCijfer)
						{
							int hcijfera = stenen[x].huidigCijfer - 1;
							ogen[hcijfera] = ogen[hcijfera] + 1;
						}
					}
				}
			}
			int tscore = 0;
			boolean klopt = false;
			for(int a = 5; a >= 0; a--)
			{
				tscore = tscore + ogen[a];
				if(ogen[a] > 3)
				{
					klopt = true;
				}
			}
			if(klopt)
			{
				if(scoreFormulier.carre == -1)
				{
					scoreFormulier.carre = tscore;
					scorevastgezet = true;
				}
				else
				{
					System.out.println("De score voor carré is al ingevuld, kies een andere optie");
				}
			}
			else
			{
				System.out.println("Sorry, u heeft geen carré gegooid");
			}
		}
		else if(yat.equals("driedezelfde"))
		{
			int[] ogen = {0, 0, 0, 0, 0, 0};
			for(int x = 0; x < stenen.length; x++)
			{
				for(int i= 0; i < stenen.length; i++)
				{
					if(stenen[x].dobbelsteennummer != stenen[i].dobbelsteennummer)
					{
						if(stenen[x].huidigCijfer == stenen[i].huidigCijfer)
						{
							int hcijfera = stenen[x].huidigCijfer - 1;
							ogen[hcijfera] = ogen[hcijfera] + 1;
						}
					}
				}
			}
			int tscore = 0;
			boolean klopt = false;
			for(int a = 5; a >= 0; a--)
			{
				tscore = tscore + ogen[a];
				if(ogen[a] > 2)
				{
					klopt = true;
				}
			}
			if(klopt)
			{
				if(scoreFormulier.driedezelfde == -1)
				{
					scoreFormulier.driedezelfde = tscore;
					scorevastgezet = true;
				}
				else
				{
					System.out.println("De score voor driedezelfde is al ingevuld, kies een andere optie");
				}
			}
			else
			{
				System.out.println("Sorry, u heeft geen driedezelfde gegooid");
			}
		}
		else if(yat.equals("een"))
		{
			int scoreeen = 0;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer == 1)
				{
					scoreeen++;
				}
			}
			if(scoreFormulier.een == -1)
			{
				scoreFormulier.een = scoreeen;	
				scorevastgezet = true;
			}
			else
			{
				System.out.println("De score voor een oog is al ingevuld, kies een andere optie");
			}
		}
		else if(yat.equals("twee"))
		{
			int scoretwee = 0;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer == 2)
				{
					scoretwee = scoretwee + 2;
				}
			}
			if(scoreFormulier.twee == -1)
			{
				scoreFormulier.twee = scoretwee;
				scorevastgezet = true;
			}
			else
			{
				System.out.println("De score voor twee ogen is al ingevuld, kies een andere optie");
			}
		}
		else if(yat.equals("drie"))
		{
			int scoredrie = 0;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer == 3)
				{
					scoredrie = scoredrie + 3;
				}
			}
			if(scoreFormulier.drie == -1)
			{
				scoreFormulier.drie = scoredrie;
				scorevastgezet = true;
			}
			else
			{
				System.out.println("De score voor drie ogen is al ingevuld, kies een andere optie");
			}
		}
		else if(yat.equals("vier"))
		{
			int scorevier = 0;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer == 4)
				{
					scorevier = scorevier + 4;
				}
			}
			if(scoreFormulier.vier == -1)
			{
				scoreFormulier.vier = scorevier;
				scorevastgezet = true;
			}
			else
			{
				System.out.println("De score voor vier ogen is al ingevuld, kies een andere optie");
			}
		}
		else if(yat.equals("vijf"))
		{
			int scorevijf = 0;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer == 5)
				{
					scorevijf = scorevijf + 5;
				}
			}
			if(scoreFormulier.vijf == -1)
			{
				scoreFormulier.vijf = scorevijf;
				scorevastgezet = true;
			}
			else
			{
				System.out.println("De score voor vijf ogen is al ingevuld, kies een andere optie");
			}
		}
		else if(yat.equals("zes"))
		{
			int scorezes = 0;
			for(Dobbelsteen steen: stenen)
			{
				if(steen.huidigCijfer == 6)
				{
					scorezes = scorezes + 6;
				}
			}
			if(scoreFormulier.zes == -1)
			{
				scoreFormulier.zes = scorezes;
				scorevastgezet = true;
			}
			else
			{
				System.out.println("De score voor zes ogen is al ingevuld, kies een andere optie");
			}
		}
		else
		{
			System.out.println("dit is geen geldig commando, kies opnieuw");
		}
		for(Dobbelsteen steen: stenen)
		{
			System.out.print(steen.huidigCijfer);
		}
		return scorevastgezet;
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
	int een = -1;
	int twee = -1;
	int drie = -1;
	int vier = -1;
	int vijf = -1;
	int zes = -1;
	int driedezelfde = -1;
	int carre = -1;
	int fullhouse = -1;
	int kleinestraat = -1;
	int grotestraat = -1;
	int yatzee = -1;
}