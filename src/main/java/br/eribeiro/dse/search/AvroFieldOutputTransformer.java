package br.eribeiro.dse.search;


import com.datastax.bdp.search.solr.FieldOutputTransformer;
import example.avro.User;
import org.apache.commons.codec.DecoderException;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.StoredFieldVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AvroFieldOutputTransformer extends FieldOutputTransformer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AvroFieldOutputTransformer.class);

    @Override
    public void stringField(FieldInfo fieldInfo,
                            String value,
                            StoredFieldVisitor visitor,
                            DocumentHelper helper) throws IOException
    {


        LOGGER.info("Avro name: " + fieldInfo.name + ", value: " + value);
        try
        {
            User user = AvroCodec.decodeHexStringAsAvroObject(value);

            FieldInfo avro_name_fi = helper.getFieldInfo("name");
            FieldInfo avro_age_fi = helper.getFieldInfo("age");
            FieldInfo avro_city_fi = helper.getFieldInfo("city");
            FieldInfo avro_country_fi = helper.getFieldInfo("country");

            if (user.getName() != null)
            {
                visitor.stringField(avro_name_fi, String.valueOf(user.getName()));
            }
            if (user.getAge() != null)
            {
                visitor.stringField(avro_age_fi, String.valueOf(user.getAge()));
            }
            if (user.getCountry() != null)
            {
                visitor.stringField(avro_country_fi, String.valueOf(user.getCountry()));
            }
            if (user.getCity() != null)
            {
                visitor.stringField(avro_city_fi, String.valueOf(user.getCity()));
            }
        }
        catch (IOException | DecoderException e)
        {
            LOGGER.error(fieldInfo.name + " " + e.getMessage());
            throw new IOException(e);
        }
    }

}
