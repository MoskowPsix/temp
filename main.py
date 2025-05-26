import os
import time
from bottle import Bottle, template, run
from w1thermsensor import W1ThermSensor

app = Bottle()
sensor = W1ThermSensor()

def read_temperature():
    try:
        temperature = sensor.get_temperature()
        return round(temperature, 1)
    except Exception as e:
        print(f"Ошибка чтения датчика температуры: {e}")
        return None

@app.route('/')
def index():
    temp = read_temperature()
    return template("index.html", temperature=temp)

@app.route("/api/status")
def api_status():
    temp = read_temperature()
    return {
        "temperature": temp,
        "timestamp": time.time()
    }

if __name__ == "__main__":
    run(app, host='0.0.0.0', port=8080)