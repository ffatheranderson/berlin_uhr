package com.ubs.opsit.interviews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeConverterImpl implements TimeConverter {

    private static final String timeFormat = "kk:mm:ss";
    private static final String brackLineSymbol = "\r\n";
    private static final String twentyFourHours = "24:00:00";
    private static final String returnValueForTwentyFourHours = "Y" + brackLineSymbol
	    + "RRRR" + brackLineSymbol
	    + "RRRR" + brackLineSymbol
	    + "OOOOOOOOOOO" + brackLineSymbol
	    + "OOOO";

    @Override
    public String convertTime(String aTime) {
	if (twentyFourHours.equals(aTime))
	    return returnValueForTwentyFourHours;

	Calendar time = Calendar.getInstance();
	try {
	    time.setTimeInMillis(new SimpleDateFormat(timeFormat).parse(aTime).getTime());
	} catch (ParseException e) {
	    throw new RuntimeException(e);
	}

	int hours = time.get(Calendar.HOUR_OF_DAY);
	int minutes = time.get(Calendar.MINUTE);
	int seconds = time.get(Calendar.SECOND);

	StringBuilder result = new StringBuilder();
	result.append(getSecondLamp(seconds))
		.append(getFiveHoursRow(hours))
		.append(getOneHoursRow(hours))
		.append(getFiveMinutesRow(minutes))
		.append(getOneMinutesRow(minutes));

	return result.toString();
    }

    private String getSecondLamp(int seconds) {
	int rest = seconds % 2;
	if (rest == 0 || seconds == 0)
	    return "Y" + brackLineSymbol;
	else
	    return "O" + brackLineSymbol;
    }

    /**
     * Returns the top red row, which is equivalent to 5 hours per section.
     * 
     * @param hours
     * @return
     */
    private String getFiveHoursRow(int hours) {
	int fullFiveHours = hours / 5;
	StringBuilder sb = new StringBuilder();

	for (int i = 0; i < fullFiveHours; i++)
	    sb.append("R");

	int length = sb.length();

	for (int i = 0; i < 4 - length; i++)
	    sb.append("O");

	return sb.append(brackLineSymbol).toString();
    }

    private String getOneHoursRow(int hours) {
	int rest = hours % 5;

	StringBuilder sb = new StringBuilder();

	for (int i = 0; i < rest; i++)
	    sb.append("R");

	int length = sb.length();

	for (int i = 0; i < 4 - length; i++)
	    sb.append("O");

	return sb.append(brackLineSymbol).toString();
    }

    private String getFiveMinutesRow(int minutes) {
	int fullFiveMinutes = minutes / 5;
	StringBuilder sb = new StringBuilder();

	for (int i = 1; i <= fullFiveMinutes; i++) {
	    boolean isQuad = i % 3 == 0;
	    if (isQuad)
		sb.append("R");
	    else
		sb.append("Y");
	}

	int length = sb.length();

	for (int i = 0; i < 11 - length; i++)
	    sb.append("O");

	return sb.append(brackLineSymbol).toString();
    }

    private String getOneMinutesRow(int minutes) {
	int rest = minutes % 5;

	StringBuilder sb = new StringBuilder();

	for (int i = 0; i < rest; i++)
	    sb.append("Y");

	int length = sb.length();

	for (int i = 0; i < 4 - length; i++)
	    sb.append("O");

	return sb.toString();
    }
}
