merge (c1: City {name: 'Москва'});
merge (c2: City {name: 'Пермь'});
merge (c3: City {name: 'Екатеринбург'});
merge (c4: City {name: 'Тюмень'});
merge (c5: City {name: 'Саратов'});
merge (c6: City {name: 'Нижневартовск'});
merge (c7: City {name: 'Архангельск'});
merge (c8: City {name: 'Санкт-Петербург'});

match (c1), (c2) where c1.name = "Москва" and c2.name = "Пермь"
merge (c2)-[:HAVE_ROUTE_TO{id: 0, transportMode:"PLANE",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Москва" and c2.name = "Пермь"
merge (c2)-[:HAVE_ROUTE_TO{id: 1, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Москва" and c2.name = "Пермь"
merge (c2)-[:HAVE_ROUTE_TO{id: 2, transportMode:"BUS",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Пермь" and c2.name = "Москва"
merge (c2)-[:HAVE_ROUTE_TO{id: 3, transportMode:"PLANE",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Пермь" and c2.name = "Москва"
merge (c2)-[:HAVE_ROUTE_TO{id: 4, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Пермь" and c2.name = "Москва"
merge (c2)-[:HAVE_ROUTE_TO{id: 5, transportMode:"BUS",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Пермь" and c2.name = "Екатеринбург"
merge (c2)-[:HAVE_ROUTE_TO{id: 6, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Екатеринбург" and c2.name = "Пермь"
merge (c2)-[:HAVE_ROUTE_TO{id: 7, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Тюмень" and c2.name = "Екатеринбург"
merge (c2)-[:HAVE_ROUTE_TO{id: 8, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Екатеринбург" and c2.name = "Тюмень"
merge (c2)-[:HAVE_ROUTE_TO{id: 9, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))}]->(c1);

match (c1), (c2) where c1.name = "Екатеринбург" and c2.name = "Архангельск"
merge (c2)-[:HAVE_ROUTE_TO{id: 10, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 3))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 4)))}]->(c1);

match (c1), (c2) where c1.name = "Архангельск" and c2.name = "Екатеринбург"
merge (c2)-[:HAVE_ROUTE_TO{id: 11, transportMode:"RAILWAY",departure:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 6))),arrival:localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 7)))}]->(c1);

// Связь Москва-Саратов (прямое сообщение)
MATCH (c1:City {name: "Москва"}), (c2:City {name: "Саратов"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 12,
  transportMode: "PLANE",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 3)))
}]->(c2);

MATCH (c1:City {name: "Москва"}), (c2:City {name: "Саратов"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 13,
  transportMode: "RAILWAY",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 3))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 4)))
}]->(c2);

// Обратное направление Саратов-Москва
MATCH (c1:City {name: "Саратов"}), (c2:City {name: "Москва"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 14,
  transportMode: "PLANE",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 4))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 5)))
}]->(c2);

MATCH (c1:City {name: "Саратов"}), (c2:City {name: "Москва"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 15,
  transportMode: "BUS",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 5))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 6)))
}]->(c2);

// Связь Тюмень-Нижневартовск (только автобус)
MATCH (c1:City {name: "Тюмень"}), (c2:City {name: "Нижневартовск"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 16,
  transportMode: "BUS",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 3)))
}]->(c2);

// Обратное направление Нижневартовск-Тюмень
MATCH (c1:City {name: "Нижневартовск"}), (c2:City {name: "Тюмень"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 17,
  transportMode: "BUS",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 4))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 5)))
}]->(c2);

// Связь Москва-Санкт-Петербург (интенсивное сообщение)
MATCH (c1:City {name: "Москва"}), (c2:City {name: "Санкт-Петербург"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 18,
  transportMode: "PLANE",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 1.2)))
}]->(c2);

MATCH (c1:City {name: "Москва"}), (c2:City {name: "Санкт-Петербург"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 19,
  transportMode: "RAILWAY",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 1.5))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2)))
}]->(c2);

// Обратное направление Санкт-Петербург-Москва
MATCH (c1:City {name: "Санкт-Петербург"}), (c2:City {name: "Москва"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 20,
  transportMode: "PLANE",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 2.5))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 3)))
}]->(c2);

MATCH (c1:City {name: "Санкт-Петербург"}), (c2:City {name: "Москва"})
MERGE (c1)-[:HAVE_ROUTE_TO {
  id: 21,
  transportMode: "RAILWAY",
  departure: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 3))),
  arrival: localdatetime(datetime.fromepochmillis(timestamp() + (1000 * 86400 * 4)))
}]->(c2);