java -jar gatewayserver-11.jar

Перед запуском команды выше, нужно указать переменные окружения
export JAVA_TOOL_OPTIONS="-javaagent:../../trace/opentelemetry-javaagent.jar"
export OTEL_SERVICE_NAME="gateway"
export OTEL_TRACES_EXPORTER=otlp
export OTEL_LOGS_EXPORTER=none
export OTEL_METRICS_EXPORTER=none 
export OTEL_EXPORTER_OTLP_TRACES_ENDPOINT=http://localhost:4318/v1/traces 