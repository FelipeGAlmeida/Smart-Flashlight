# Smart-Flashlight

> EN:

This is my first application using services. It also uses some sensors (light and proximity) and, if user wants,
works even the application was totally closed.

## How it works:
- Once opened, it is possible to see a measurement (~~Lumens~~) that measures the luminosity of the environment in real time.
- When the ambient light becomes less than 10 (not configurable), the flashlight automatically turns on.
- There is an option available ("Never let me in the dark") that, when enabled, causes the application to run even closed.
- In cases where the light sensor is obstructed, the flashlight could accidentally turn on, so the proximity sensor
is used. Therefore, when the proximity sensor detects that something prevents the correct measurement of luminosity the flashlight
automatically turns off.

Then, if ~~Lumens~~ < 10 && !SensorObstructed = Flashlight On;

## Used components:
- Services
- Start at boot
- Light sensor
- Proximity sensor
- CircleProgress (Lzyzsd)


> PT:

Este é meu primeiro aplicativo usando serviços. Ele tambem usa alguns sensores (luz e proximidade) e, caso o usuário queira,
funciona mesmo com a aplicação totalmente fechada.

## Como funciona:
- Uma vez aberto, é possível visualizar uma medida (~~Lumens~~) que mede a luminosidade do ambiente em tempo real.
- Quando a luminosidade do ambiente se torna menor que 10 (não configurável), a lanterna automaticamente é ativada.
- Há uma opção disponível ("Never let me in the dark") que, quando ativada, faz o aplicativo executar mesmo fechado.
- Para casos em que o sensor de luz for obstruído, para que a lanterna não se acenda acidentalmente o sensor de proximidade
é utilizado. Logo, quando o sensor de proximidade detecta que algo impede a medição correta de luminosidade a lanterna
automaticamente é desligada.

Então, caso ~~Lumens~~ < 10 && !SensorObstruído = Lanterna acesa;

## Componentes utilizados:
- Services
- Start at boot
- Sensor de luz
- Sensor de proximidade
- CircleProgress (Lzyzsd)
