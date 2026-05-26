# Frontend – Tehnički pregled vozila

Angular 19 aplikacija (NgModule arhitektura) za vođenje kroz korake tehničkog pregleda.

## Struktura

```
src/app/
  config/           # API i WebSocket URL
  models/           # Tipovi i enumi
  dto/              # request/ i response/ DTO mapiranje na backend
  services/         # API, sesija, flow, WebSocket, dijalog
  ui-kit/           # Zajedničke UI komponente (dugme, input, checkbox, dropdown, ...)
  pages/inspection/ # Stranice formi po koracima pregleda
  core/             # HTTP interceptor za greške
```

## Pokretanje

```bash
cd frontend
npm install
npm start
```

Aplikacija: http://localhost:4200

Backend mora raditi na `http://localhost:8080` (CORS je podešen).

Za test kočnica pokrenuti i Python simulator:

```bash
cd "../Brake service"
python brake-simulator.py
```

## Redosled koraka

1. Unos vozila → 2. Preduslovi → 3. Identifikacija → 4. Gume/točkovi → 5. Motor → 6. Kočiona tečnost → 7. Izduvni → 8. Ogibljenje → 9. Elektro → 10. Test kočnica (loading + WebSocket) → 11. Svetla → 12. Oprema → 13. Zaključak

## WebSocket

STOMP preko SockJS na `/ws`, topic: `/topic/inspection/{id}/brake-test` — obaveštenje stiže kada `BrakeKafkaConsumer` obradi `brake-test-finished` poruku.
