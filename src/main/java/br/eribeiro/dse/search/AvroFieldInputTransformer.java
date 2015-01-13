package br.eribeiro.dse.search;

import com.datastax.bdp.search.solr.FieldInputTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.avro.User;
import org.apache.lucene.document.Document;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AvroFieldInputTransformer extends FieldInputTransformer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AvroFieldInputTransformer.class);
    
    @Override
    public boolean evaluate(String field)
    {
        return field.equals("post");
    }

    @Override
    public void addFieldToDocument(SolrCore core, IndexSchema schema, String key, Document doc, SchemaField fieldInfo, String fieldValue, float boost, DocumentHelper helper) throws IOException
    {
        try
        {

            LOGGER.info("Avro AvroFieldInputTransformer called");
            LOGGER.info("Avro fieldValue: " + fieldValue);
            
            User user = AvroCodec.decodeHexStringAsAvroObject(fieldValue);
            
            LOGGER.info("Avro => " + user.toString());

            SchemaField avroName = core.getLatestSchema().getFieldOrNull("name");
            SchemaField avroAge = core.getLatestSchema().getFieldOrNull("age");
            SchemaField avroCity = core.getLatestSchema().getFieldOrNull("city");
            SchemaField avroCountry = core.getLatestSchema().getFieldOrNull("country");

            helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, avroName, String.valueOf(user.getName()), boost);
            helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, avroAge, String.valueOf(user.getAge()), boost);
            helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, avroCity, String.valueOf(user.getCity()), boost);
            helper.addFieldToDocument(core, core.getLatestSchema(), key, doc, avroCountry, String.valueOf(user.getCountry()), boost);
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
