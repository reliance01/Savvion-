import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Temp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String startDate = new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse("07-01-2020  23:59:59"));

			System.out.println(startDate);

			String endDate = new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").parse("07-03-2020  23:59:59"));

			System.out.println(endDate);

			// Jul 01, 2020 12:00 AM

			SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
			long temp = format.parse(startDate).getTime();
			System.out.println(temp);
			long temp2 = format.parse(endDate).getTime();
			System.out.println(temp2);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
