package springboot;

import org.thymeleaf.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Convert
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if(attribute == null || attribute.size() ==0)
            return null;
        return String.join(SPLIT_CHAR,attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        List<String> list = new ArrayList<>();
        if(StringUtils.isEmpty(dbData))
            return list;

       return list = Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}
