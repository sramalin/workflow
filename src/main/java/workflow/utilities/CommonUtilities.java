package workflow.utilities;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import workflow.domain.Ticket;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Created by sramalin on 12/06/17.
 */
@Component
public class CommonUtilities {



    public <T> List<T> loadObjectList(Class<T> type, byte[] csvFile) {
        try {

            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<T> readValues = mapper.readerWithSchemaFor(type).with(bootstrapSchema).readValues(csvFile);
            return readValues.readAll();

        } catch (Exception e) {
            System.out.println("Error occurred while loading object list from file " +  e.getMessage());
            return Collections.emptyList();
        }
    }


}
