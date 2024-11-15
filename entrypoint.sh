#!/bin/bash

# Load environment variables from the .env file
if [ -f "/app/.env" ]; then
  echo "Loading environment variables from .env file"
  export $(grep -v '^#' /app/.env | xargs)
fi

# Start the application
echo "Starting the application..."
exec java -Dvertx.disableDnsResolver=true -Djava.net.preferIPv4Stack=true -jar app.jar
