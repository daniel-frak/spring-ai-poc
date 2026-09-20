#!/bin/bash
set -e

# Start Ollama in the background.
/bin/ollama serve &
# Record Process ID.
pid=$!

echo "Waiting for Ollama..."

until ollama list > /dev/null 2>&1; do
    sleep 1
done

echo "Ollama is ready."

echo "🔴 Retrieving llama3.1:8b..."
ollama pull llama3.1:8b

echo "🔴 Retrieving embedding model..."
ollama pull nomic-embed-text

echo "🟢 Done!"

wait $pid
