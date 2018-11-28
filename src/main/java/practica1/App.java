package practica1;

public class App {

  private final static java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(App.class.getName());

  public static void main(String[] args) {

    DataBase ds = new DataBase();

    // Inicializar la base de datos.
    org.json.JSONArray documents = ds.init();

    if (documents.length() > 0) {

      //LOGGER.info("Documentos -> " + documents.toString(4));

      java.util.Set<String> euCodes = new java.util.HashSet<>();
      org.json.JSONArray expedientes = new org.json.JSONArray();

      documents.forEach(d -> {

        org.json.JSONObject expediente = new org.json.JSONObject();

        org.json.JSONObject document = (org.json.JSONObject) d;

        if(!euCodes.contains(document.getString("cod_eu"))){
          euCodes.add(document.getString("cod_eu"));

          expediente.put("cod_eu", document.getString("cod_eu"));
          expediente.put("family_name", document.getString("family_name"));
          //expediente.put("documents", new org.json.JSONArray());
          expedientes.put(expediente);
        }

      });

      //LOGGER.info("Expedientes -> " + expedientes.toString(4));

      org.json.JSONArray D001 = arrayToStream(documents)
              .map(org.json.JSONObject.class::cast)
              .filter(filter("cod_eu", "D001"))
              .collect(java.util.stream.Collector.of(
                      org.json.JSONArray::new, // acumulador de inicio
                      org.json.JSONArray::put, // procesar cada elemento
                      org.json.JSONArray::put  // Confluencia de 2 acumuladores en ejecución paralela.
              ));

      org.json.JSONArray D002 = arrayToStream(documents)
              .map(org.json.JSONObject.class::cast)
              .filter(filter("cod_eu", "D002"))
              .collect(java.util.stream.Collector.of(
              org.json.JSONArray::new, // acumulador de inicio
              org.json.JSONArray::put, // procesar cada elemento
              org.json.JSONArray::put  // Confluencia de 2 acumuladores en ejecución paralela.
      ));

      org.json.JSONArray D003 = arrayToStream(documents)
              .map(org.json.JSONObject.class::cast)
              .filter(filter("cod_eu", "D003"))
              .collect(java.util.stream.Collector.of(
                      org.json.JSONArray::new, // acumulador de inicio
                      org.json.JSONArray::put, // procesar cada elemento
                      org.json.JSONArray::put  // Confluencia de 2 acumuladores en ejecución paralela.
              ));

      org.json.JSONArray D004 = arrayToStream(documents)
              .map(org.json.JSONObject.class::cast)
              .filter(filter("cod_eu", "D004"))
              .collect(java.util.stream.Collector.of(
                      org.json.JSONArray::new, // acumulador de inicio
                      org.json.JSONArray::put, // procesar cada elemento
                      org.json.JSONArray::put  // Confluencia de 2 acumuladores en ejecución paralela.
              ));

      expedientes.forEach(e -> {

        org.json.JSONObject expediente = (org.json.JSONObject) e;

        switch (expediente.getString("cod_eu")) {

          case "D001":
            expediente.put("documents", D001);
            break;
          case "D002":
            expediente.put("documents", D002);
            break;
          case "D003":
            expediente.put("documents", D003);
            break;
          case "D004":
            expediente.put("documents", D004);
            break;
        }

      });


      LOGGER.info("Resultado Final: /n");
      LOGGER.info("Expedientes -> " + expedientes.toString(4));

    }else{
      LOGGER.warning("No hay documentos en la base de datos.");
    }

  }

  /**
   * Representa un predicado (función de valor booleano) de un argumento.
   *
   * @param key Una cadena clave.
   * @param value Valor a filtrar.
   * @return Un predicado.
   */
  public static java.util.function.Predicate<? super org.json.JSONObject> filter (String key, String value) {
    return d -> d.get(key).equals(value);
  }

  /**
   * Convierte un org.json.JSONArray a un stream.
   *
   * @param array Arreglo de objetos JSON.
   * @return Un stream (flujo) en base a un arreglo (org.json.JSONArray).
   */
  private static java.util.stream.Stream<Object> arrayToStream(org.json.JSONArray array) {
    // array.spliterator() retorna un spliterator que en sí es un iterador por cada arreglo.
    /**
     * java.util.stream.StreamSupport.stream
     *
     * Crea una nueva secuencia secuencial o paralela desde un Supplier de Spliterator.
     * Este método también especifica las características del Spliterator suministrado.
     */
    return java.util.stream.StreamSupport.stream(array.spliterator(), false);
  }

}
