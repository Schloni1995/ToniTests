package myQueryConverter;

import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;

public class SQL_Beautifier
{

	public static void main(final String[] args)
	{
		final String tester = "select * from Personal where ausgeschieden = 0 and PersonalArt <> 'Pauschalkraft' and Vorname <> '' and Anrede <> '' and PersonalArt <> ''";
		new SQL_Beautifier(tester).returnFormattedQuery();
	}

	private final String qry;

	public SQL_Beautifier(final String qry)
	{
		this.qry = qry;
	}

	public String returnFormattedQuery()
	{
		return new BasicFormatterImpl().format(qry);
	}
}
