package controller.supportClasses;


import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A helper classes with function to return a swedish date format
 */
public class SwedishDateFormat
{
    public StringConverter<LocalDate> getSwedishDateConverter()
    {
        StringConverter<LocalDate> swedishDate = new StringConverter<LocalDate>()
        {
            final String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date)
            {
                if(date != null)
                {
                    return dateFormatter.format(date);
                }
                else
                {
                    return "";
                }
            }
        
            @Override
            public LocalDate fromString(String string)
            {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        return swedishDate;
    }
}
