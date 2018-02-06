/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package cn.xukai.spark.dataframe.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

/**
 * Step - 1
 * 此文件是通过下面命令生成后，拷贝到项目中的。
 * java -jar avro-tools-1.8.2.jar compile schema CustomerAddress.avsc .
 *
 */
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class CustomerAddress extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6084597197414058149L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CustomerAddress\",\"namespace\":\"cn.xukai.spark.dataframe.avro\",\"fields\":[{\"name\":\"ca_address_sk\",\"type\":\"long\"},{\"name\":\"ca_address_id\",\"type\":\"string\"},{\"name\":\"ca_street_number\",\"type\":\"string\"},{\"name\":\"ca_street_name\",\"type\":\"string\"},{\"name\":\"ca_street_type\",\"type\":\"string\"},{\"name\":\"ca_suite_number\",\"type\":\"string\"},{\"name\":\"ca_city\",\"type\":\"string\"},{\"name\":\"ca_county\",\"type\":\"string\"},{\"name\":\"ca_state\",\"type\":\"string\"},{\"name\":\"ca_zip\",\"type\":\"string\"},{\"name\":\"ca_country\",\"type\":\"string\"},{\"name\":\"ca_gmt_offset\",\"type\":\"double\"},{\"name\":\"ca_location_type\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CustomerAddress> ENCODER =
      new BinaryMessageEncoder<CustomerAddress>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CustomerAddress> DECODER =
      new BinaryMessageDecoder<CustomerAddress>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<CustomerAddress> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<CustomerAddress> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CustomerAddress>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this CustomerAddress to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a CustomerAddress from a ByteBuffer. */
  public static CustomerAddress fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public long ca_address_sk;
  @Deprecated public CharSequence ca_address_id;
  @Deprecated public CharSequence ca_street_number;
  @Deprecated public CharSequence ca_street_name;
  @Deprecated public CharSequence ca_street_type;
  @Deprecated public CharSequence ca_suite_number;
  @Deprecated public CharSequence ca_city;
  @Deprecated public CharSequence ca_county;
  @Deprecated public CharSequence ca_state;
  @Deprecated public CharSequence ca_zip;
  @Deprecated public CharSequence ca_country;
  @Deprecated public double ca_gmt_offset;
  @Deprecated public CharSequence ca_location_type;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CustomerAddress() {}

  /**
   * All-args constructor.
   * @param ca_address_sk The new value for ca_address_sk
   * @param ca_address_id The new value for ca_address_id
   * @param ca_street_number The new value for ca_street_number
   * @param ca_street_name The new value for ca_street_name
   * @param ca_street_type The new value for ca_street_type
   * @param ca_suite_number The new value for ca_suite_number
   * @param ca_city The new value for ca_city
   * @param ca_county The new value for ca_county
   * @param ca_state The new value for ca_state
   * @param ca_zip The new value for ca_zip
   * @param ca_country The new value for ca_country
   * @param ca_gmt_offset The new value for ca_gmt_offset
   * @param ca_location_type The new value for ca_location_type
   */
  public CustomerAddress(Long ca_address_sk, CharSequence ca_address_id, CharSequence ca_street_number, CharSequence ca_street_name, CharSequence ca_street_type, CharSequence ca_suite_number, CharSequence ca_city, CharSequence ca_county, CharSequence ca_state, CharSequence ca_zip, CharSequence ca_country, Double ca_gmt_offset, CharSequence ca_location_type) {
    this.ca_address_sk = ca_address_sk;
    this.ca_address_id = ca_address_id;
    this.ca_street_number = ca_street_number;
    this.ca_street_name = ca_street_name;
    this.ca_street_type = ca_street_type;
    this.ca_suite_number = ca_suite_number;
    this.ca_city = ca_city;
    this.ca_county = ca_county;
    this.ca_state = ca_state;
    this.ca_zip = ca_zip;
    this.ca_country = ca_country;
    this.ca_gmt_offset = ca_gmt_offset;
    this.ca_location_type = ca_location_type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return ca_address_sk;
    case 1: return ca_address_id;
    case 2: return ca_street_number;
    case 3: return ca_street_name;
    case 4: return ca_street_type;
    case 5: return ca_suite_number;
    case 6: return ca_city;
    case 7: return ca_county;
    case 8: return ca_state;
    case 9: return ca_zip;
    case 10: return ca_country;
    case 11: return ca_gmt_offset;
    case 12: return ca_location_type;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: ca_address_sk = (Long)value$; break;
    case 1: ca_address_id = (CharSequence)value$; break;
    case 2: ca_street_number = (CharSequence)value$; break;
    case 3: ca_street_name = (CharSequence)value$; break;
    case 4: ca_street_type = (CharSequence)value$; break;
    case 5: ca_suite_number = (CharSequence)value$; break;
    case 6: ca_city = (CharSequence)value$; break;
    case 7: ca_county = (CharSequence)value$; break;
    case 8: ca_state = (CharSequence)value$; break;
    case 9: ca_zip = (CharSequence)value$; break;
    case 10: ca_country = (CharSequence)value$; break;
    case 11: ca_gmt_offset = (Double)value$; break;
    case 12: ca_location_type = (CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'ca_address_sk' field.
   * @return The value of the 'ca_address_sk' field.
   */
  public Long getCaAddressSk() {
    return ca_address_sk;
  }

  /**
   * Sets the value of the 'ca_address_sk' field.
   * @param value the value to set.
   */
  public void setCaAddressSk(Long value) {
    this.ca_address_sk = value;
  }

  /**
   * Gets the value of the 'ca_address_id' field.
   * @return The value of the 'ca_address_id' field.
   */
  public CharSequence getCaAddressId() {
    return ca_address_id;
  }

  /**
   * Sets the value of the 'ca_address_id' field.
   * @param value the value to set.
   */
  public void setCaAddressId(CharSequence value) {
    this.ca_address_id = value;
  }

  /**
   * Gets the value of the 'ca_street_number' field.
   * @return The value of the 'ca_street_number' field.
   */
  public CharSequence getCaStreetNumber() {
    return ca_street_number;
  }

  /**
   * Sets the value of the 'ca_street_number' field.
   * @param value the value to set.
   */
  public void setCaStreetNumber(CharSequence value) {
    this.ca_street_number = value;
  }

  /**
   * Gets the value of the 'ca_street_name' field.
   * @return The value of the 'ca_street_name' field.
   */
  public CharSequence getCaStreetName() {
    return ca_street_name;
  }

  /**
   * Sets the value of the 'ca_street_name' field.
   * @param value the value to set.
   */
  public void setCaStreetName(CharSequence value) {
    this.ca_street_name = value;
  }

  /**
   * Gets the value of the 'ca_street_type' field.
   * @return The value of the 'ca_street_type' field.
   */
  public CharSequence getCaStreetType() {
    return ca_street_type;
  }

  /**
   * Sets the value of the 'ca_street_type' field.
   * @param value the value to set.
   */
  public void setCaStreetType(CharSequence value) {
    this.ca_street_type = value;
  }

  /**
   * Gets the value of the 'ca_suite_number' field.
   * @return The value of the 'ca_suite_number' field.
   */
  public CharSequence getCaSuiteNumber() {
    return ca_suite_number;
  }

  /**
   * Sets the value of the 'ca_suite_number' field.
   * @param value the value to set.
   */
  public void setCaSuiteNumber(CharSequence value) {
    this.ca_suite_number = value;
  }

  /**
   * Gets the value of the 'ca_city' field.
   * @return The value of the 'ca_city' field.
   */
  public CharSequence getCaCity() {
    return ca_city;
  }

  /**
   * Sets the value of the 'ca_city' field.
   * @param value the value to set.
   */
  public void setCaCity(CharSequence value) {
    this.ca_city = value;
  }

  /**
   * Gets the value of the 'ca_county' field.
   * @return The value of the 'ca_county' field.
   */
  public CharSequence getCaCounty() {
    return ca_county;
  }

  /**
   * Sets the value of the 'ca_county' field.
   * @param value the value to set.
   */
  public void setCaCounty(CharSequence value) {
    this.ca_county = value;
  }

  /**
   * Gets the value of the 'ca_state' field.
   * @return The value of the 'ca_state' field.
   */
  public CharSequence getCaState() {
    return ca_state;
  }

  /**
   * Sets the value of the 'ca_state' field.
   * @param value the value to set.
   */
  public void setCaState(CharSequence value) {
    this.ca_state = value;
  }

  /**
   * Gets the value of the 'ca_zip' field.
   * @return The value of the 'ca_zip' field.
   */
  public CharSequence getCaZip() {
    return ca_zip;
  }

  /**
   * Sets the value of the 'ca_zip' field.
   * @param value the value to set.
   */
  public void setCaZip(CharSequence value) {
    this.ca_zip = value;
  }

  /**
   * Gets the value of the 'ca_country' field.
   * @return The value of the 'ca_country' field.
   */
  public CharSequence getCaCountry() {
    return ca_country;
  }

  /**
   * Sets the value of the 'ca_country' field.
   * @param value the value to set.
   */
  public void setCaCountry(CharSequence value) {
    this.ca_country = value;
  }

  /**
   * Gets the value of the 'ca_gmt_offset' field.
   * @return The value of the 'ca_gmt_offset' field.
   */
  public Double getCaGmtOffset() {
    return ca_gmt_offset;
  }

  /**
   * Sets the value of the 'ca_gmt_offset' field.
   * @param value the value to set.
   */
  public void setCaGmtOffset(Double value) {
    this.ca_gmt_offset = value;
  }

  /**
   * Gets the value of the 'ca_location_type' field.
   * @return The value of the 'ca_location_type' field.
   */
  public CharSequence getCaLocationType() {
    return ca_location_type;
  }

  /**
   * Sets the value of the 'ca_location_type' field.
   * @param value the value to set.
   */
  public void setCaLocationType(CharSequence value) {
    this.ca_location_type = value;
  }

  /**
   * Creates a new CustomerAddress RecordBuilder.
   * @return A new CustomerAddress RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new CustomerAddress RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CustomerAddress RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    return new Builder(other);
  }

  /**
   * Creates a new CustomerAddress RecordBuilder by copying an existing CustomerAddress instance.
   * @param other The existing instance to copy.
   * @return A new CustomerAddress RecordBuilder
   */
  public static Builder newBuilder(CustomerAddress other) {
    return new Builder(other);
  }

  /**
   * RecordBuilder for CustomerAddress instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CustomerAddress>
    implements org.apache.avro.data.RecordBuilder<CustomerAddress> {

    private long ca_address_sk;
    private CharSequence ca_address_id;
    private CharSequence ca_street_number;
    private CharSequence ca_street_name;
    private CharSequence ca_street_type;
    private CharSequence ca_suite_number;
    private CharSequence ca_city;
    private CharSequence ca_county;
    private CharSequence ca_state;
    private CharSequence ca_zip;
    private CharSequence ca_country;
    private double ca_gmt_offset;
    private CharSequence ca_location_type;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.ca_address_sk)) {
        this.ca_address_sk = data().deepCopy(fields()[0].schema(), other.ca_address_sk);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ca_address_id)) {
        this.ca_address_id = data().deepCopy(fields()[1].schema(), other.ca_address_id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.ca_street_number)) {
        this.ca_street_number = data().deepCopy(fields()[2].schema(), other.ca_street_number);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.ca_street_name)) {
        this.ca_street_name = data().deepCopy(fields()[3].schema(), other.ca_street_name);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.ca_street_type)) {
        this.ca_street_type = data().deepCopy(fields()[4].schema(), other.ca_street_type);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.ca_suite_number)) {
        this.ca_suite_number = data().deepCopy(fields()[5].schema(), other.ca_suite_number);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.ca_city)) {
        this.ca_city = data().deepCopy(fields()[6].schema(), other.ca_city);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.ca_county)) {
        this.ca_county = data().deepCopy(fields()[7].schema(), other.ca_county);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.ca_state)) {
        this.ca_state = data().deepCopy(fields()[8].schema(), other.ca_state);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.ca_zip)) {
        this.ca_zip = data().deepCopy(fields()[9].schema(), other.ca_zip);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.ca_country)) {
        this.ca_country = data().deepCopy(fields()[10].schema(), other.ca_country);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.ca_gmt_offset)) {
        this.ca_gmt_offset = data().deepCopy(fields()[11].schema(), other.ca_gmt_offset);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.ca_location_type)) {
        this.ca_location_type = data().deepCopy(fields()[12].schema(), other.ca_location_type);
        fieldSetFlags()[12] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing CustomerAddress instance
     * @param other The existing instance to copy.
     */
    private Builder(CustomerAddress other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.ca_address_sk)) {
        this.ca_address_sk = data().deepCopy(fields()[0].schema(), other.ca_address_sk);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.ca_address_id)) {
        this.ca_address_id = data().deepCopy(fields()[1].schema(), other.ca_address_id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.ca_street_number)) {
        this.ca_street_number = data().deepCopy(fields()[2].schema(), other.ca_street_number);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.ca_street_name)) {
        this.ca_street_name = data().deepCopy(fields()[3].schema(), other.ca_street_name);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.ca_street_type)) {
        this.ca_street_type = data().deepCopy(fields()[4].schema(), other.ca_street_type);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.ca_suite_number)) {
        this.ca_suite_number = data().deepCopy(fields()[5].schema(), other.ca_suite_number);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.ca_city)) {
        this.ca_city = data().deepCopy(fields()[6].schema(), other.ca_city);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.ca_county)) {
        this.ca_county = data().deepCopy(fields()[7].schema(), other.ca_county);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.ca_state)) {
        this.ca_state = data().deepCopy(fields()[8].schema(), other.ca_state);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.ca_zip)) {
        this.ca_zip = data().deepCopy(fields()[9].schema(), other.ca_zip);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.ca_country)) {
        this.ca_country = data().deepCopy(fields()[10].schema(), other.ca_country);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.ca_gmt_offset)) {
        this.ca_gmt_offset = data().deepCopy(fields()[11].schema(), other.ca_gmt_offset);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.ca_location_type)) {
        this.ca_location_type = data().deepCopy(fields()[12].schema(), other.ca_location_type);
        fieldSetFlags()[12] = true;
      }
    }

    /**
      * Gets the value of the 'ca_address_sk' field.
      * @return The value.
      */
    public Long getCaAddressSk() {
      return ca_address_sk;
    }

    /**
      * Sets the value of the 'ca_address_sk' field.
      * @param value The value of 'ca_address_sk'.
      * @return This builder.
      */
    public Builder setCaAddressSk(long value) {
      validate(fields()[0], value);
      this.ca_address_sk = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_address_sk' field has been set.
      * @return True if the 'ca_address_sk' field has been set, false otherwise.
      */
    public boolean hasCaAddressSk() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'ca_address_sk' field.
      * @return This builder.
      */
    public Builder clearCaAddressSk() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_address_id' field.
      * @return The value.
      */
    public CharSequence getCaAddressId() {
      return ca_address_id;
    }

    /**
      * Sets the value of the 'ca_address_id' field.
      * @param value The value of 'ca_address_id'.
      * @return This builder.
      */
    public Builder setCaAddressId(CharSequence value) {
      validate(fields()[1], value);
      this.ca_address_id = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_address_id' field has been set.
      * @return True if the 'ca_address_id' field has been set, false otherwise.
      */
    public boolean hasCaAddressId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'ca_address_id' field.
      * @return This builder.
      */
    public Builder clearCaAddressId() {
      ca_address_id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_street_number' field.
      * @return The value.
      */
    public CharSequence getCaStreetNumber() {
      return ca_street_number;
    }

    /**
      * Sets the value of the 'ca_street_number' field.
      * @param value The value of 'ca_street_number'.
      * @return This builder.
      */
    public Builder setCaStreetNumber(CharSequence value) {
      validate(fields()[2], value);
      this.ca_street_number = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_street_number' field has been set.
      * @return True if the 'ca_street_number' field has been set, false otherwise.
      */
    public boolean hasCaStreetNumber() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'ca_street_number' field.
      * @return This builder.
      */
    public Builder clearCaStreetNumber() {
      ca_street_number = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_street_name' field.
      * @return The value.
      */
    public CharSequence getCaStreetName() {
      return ca_street_name;
    }

    /**
      * Sets the value of the 'ca_street_name' field.
      * @param value The value of 'ca_street_name'.
      * @return This builder.
      */
    public Builder setCaStreetName(CharSequence value) {
      validate(fields()[3], value);
      this.ca_street_name = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_street_name' field has been set.
      * @return True if the 'ca_street_name' field has been set, false otherwise.
      */
    public boolean hasCaStreetName() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'ca_street_name' field.
      * @return This builder.
      */
    public Builder clearCaStreetName() {
      ca_street_name = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_street_type' field.
      * @return The value.
      */
    public CharSequence getCaStreetType() {
      return ca_street_type;
    }

    /**
      * Sets the value of the 'ca_street_type' field.
      * @param value The value of 'ca_street_type'.
      * @return This builder.
      */
    public Builder setCaStreetType(CharSequence value) {
      validate(fields()[4], value);
      this.ca_street_type = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_street_type' field has been set.
      * @return True if the 'ca_street_type' field has been set, false otherwise.
      */
    public boolean hasCaStreetType() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'ca_street_type' field.
      * @return This builder.
      */
    public Builder clearCaStreetType() {
      ca_street_type = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_suite_number' field.
      * @return The value.
      */
    public CharSequence getCaSuiteNumber() {
      return ca_suite_number;
    }

    /**
      * Sets the value of the 'ca_suite_number' field.
      * @param value The value of 'ca_suite_number'.
      * @return This builder.
      */
    public Builder setCaSuiteNumber(CharSequence value) {
      validate(fields()[5], value);
      this.ca_suite_number = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_suite_number' field has been set.
      * @return True if the 'ca_suite_number' field has been set, false otherwise.
      */
    public boolean hasCaSuiteNumber() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'ca_suite_number' field.
      * @return This builder.
      */
    public Builder clearCaSuiteNumber() {
      ca_suite_number = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_city' field.
      * @return The value.
      */
    public CharSequence getCaCity() {
      return ca_city;
    }

    /**
      * Sets the value of the 'ca_city' field.
      * @param value The value of 'ca_city'.
      * @return This builder.
      */
    public Builder setCaCity(CharSequence value) {
      validate(fields()[6], value);
      this.ca_city = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_city' field has been set.
      * @return True if the 'ca_city' field has been set, false otherwise.
      */
    public boolean hasCaCity() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'ca_city' field.
      * @return This builder.
      */
    public Builder clearCaCity() {
      ca_city = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_county' field.
      * @return The value.
      */
    public CharSequence getCaCounty() {
      return ca_county;
    }

    /**
      * Sets the value of the 'ca_county' field.
      * @param value The value of 'ca_county'.
      * @return This builder.
      */
    public Builder setCaCounty(CharSequence value) {
      validate(fields()[7], value);
      this.ca_county = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_county' field has been set.
      * @return True if the 'ca_county' field has been set, false otherwise.
      */
    public boolean hasCaCounty() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'ca_county' field.
      * @return This builder.
      */
    public Builder clearCaCounty() {
      ca_county = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_state' field.
      * @return The value.
      */
    public CharSequence getCaState() {
      return ca_state;
    }

    /**
      * Sets the value of the 'ca_state' field.
      * @param value The value of 'ca_state'.
      * @return This builder.
      */
    public Builder setCaState(CharSequence value) {
      validate(fields()[8], value);
      this.ca_state = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_state' field has been set.
      * @return True if the 'ca_state' field has been set, false otherwise.
      */
    public boolean hasCaState() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'ca_state' field.
      * @return This builder.
      */
    public Builder clearCaState() {
      ca_state = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_zip' field.
      * @return The value.
      */
    public CharSequence getCaZip() {
      return ca_zip;
    }

    /**
      * Sets the value of the 'ca_zip' field.
      * @param value The value of 'ca_zip'.
      * @return This builder.
      */
    public Builder setCaZip(CharSequence value) {
      validate(fields()[9], value);
      this.ca_zip = value;
      fieldSetFlags()[9] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_zip' field has been set.
      * @return True if the 'ca_zip' field has been set, false otherwise.
      */
    public boolean hasCaZip() {
      return fieldSetFlags()[9];
    }


    /**
      * Clears the value of the 'ca_zip' field.
      * @return This builder.
      */
    public Builder clearCaZip() {
      ca_zip = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_country' field.
      * @return The value.
      */
    public CharSequence getCaCountry() {
      return ca_country;
    }

    /**
      * Sets the value of the 'ca_country' field.
      * @param value The value of 'ca_country'.
      * @return This builder.
      */
    public Builder setCaCountry(CharSequence value) {
      validate(fields()[10], value);
      this.ca_country = value;
      fieldSetFlags()[10] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_country' field has been set.
      * @return True if the 'ca_country' field has been set, false otherwise.
      */
    public boolean hasCaCountry() {
      return fieldSetFlags()[10];
    }


    /**
      * Clears the value of the 'ca_country' field.
      * @return This builder.
      */
    public Builder clearCaCountry() {
      ca_country = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_gmt_offset' field.
      * @return The value.
      */
    public Double getCaGmtOffset() {
      return ca_gmt_offset;
    }

    /**
      * Sets the value of the 'ca_gmt_offset' field.
      * @param value The value of 'ca_gmt_offset'.
      * @return This builder.
      */
    public Builder setCaGmtOffset(double value) {
      validate(fields()[11], value);
      this.ca_gmt_offset = value;
      fieldSetFlags()[11] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_gmt_offset' field has been set.
      * @return True if the 'ca_gmt_offset' field has been set, false otherwise.
      */
    public boolean hasCaGmtOffset() {
      return fieldSetFlags()[11];
    }


    /**
      * Clears the value of the 'ca_gmt_offset' field.
      * @return This builder.
      */
    public Builder clearCaGmtOffset() {
      fieldSetFlags()[11] = false;
      return this;
    }

    /**
      * Gets the value of the 'ca_location_type' field.
      * @return The value.
      */
    public CharSequence getCaLocationType() {
      return ca_location_type;
    }

    /**
      * Sets the value of the 'ca_location_type' field.
      * @param value The value of 'ca_location_type'.
      * @return This builder.
      */
    public Builder setCaLocationType(CharSequence value) {
      validate(fields()[12], value);
      this.ca_location_type = value;
      fieldSetFlags()[12] = true;
      return this;
    }

    /**
      * Checks whether the 'ca_location_type' field has been set.
      * @return True if the 'ca_location_type' field has been set, false otherwise.
      */
    public boolean hasCaLocationType() {
      return fieldSetFlags()[12];
    }


    /**
      * Clears the value of the 'ca_location_type' field.
      * @return This builder.
      */
    public Builder clearCaLocationType() {
      ca_location_type = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CustomerAddress build() {
      try {
        CustomerAddress record = new CustomerAddress();
        record.ca_address_sk = fieldSetFlags()[0] ? this.ca_address_sk : (Long) defaultValue(fields()[0]);
        record.ca_address_id = fieldSetFlags()[1] ? this.ca_address_id : (CharSequence) defaultValue(fields()[1]);
        record.ca_street_number = fieldSetFlags()[2] ? this.ca_street_number : (CharSequence) defaultValue(fields()[2]);
        record.ca_street_name = fieldSetFlags()[3] ? this.ca_street_name : (CharSequence) defaultValue(fields()[3]);
        record.ca_street_type = fieldSetFlags()[4] ? this.ca_street_type : (CharSequence) defaultValue(fields()[4]);
        record.ca_suite_number = fieldSetFlags()[5] ? this.ca_suite_number : (CharSequence) defaultValue(fields()[5]);
        record.ca_city = fieldSetFlags()[6] ? this.ca_city : (CharSequence) defaultValue(fields()[6]);
        record.ca_county = fieldSetFlags()[7] ? this.ca_county : (CharSequence) defaultValue(fields()[7]);
        record.ca_state = fieldSetFlags()[8] ? this.ca_state : (CharSequence) defaultValue(fields()[8]);
        record.ca_zip = fieldSetFlags()[9] ? this.ca_zip : (CharSequence) defaultValue(fields()[9]);
        record.ca_country = fieldSetFlags()[10] ? this.ca_country : (CharSequence) defaultValue(fields()[10]);
        record.ca_gmt_offset = fieldSetFlags()[11] ? this.ca_gmt_offset : (Double) defaultValue(fields()[11]);
        record.ca_location_type = fieldSetFlags()[12] ? this.ca_location_type : (CharSequence) defaultValue(fields()[12]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CustomerAddress>
    WRITER$ = (org.apache.avro.io.DatumWriter<CustomerAddress>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CustomerAddress>
    READER$ = (org.apache.avro.io.DatumReader<CustomerAddress>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
