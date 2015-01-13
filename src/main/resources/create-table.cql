drop keyspace blog;

CREATE KEYSPACE blog WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE blog;

create table solr (
  "id" INT PRIMARY KEY,
  "json" VARCHAR);

insert into solr (id, json) values (1, '{"city":"brasilia","state":"df", "country":"brasil"}');


insert into solr (id, json) values (2, '{"city":"goiania","state":"go", "country":"brasil"}');


insert into solr (id, json) values (3, '{"city":"rio de janeiro","state":"rj", "country":"brasil"}');
    
    