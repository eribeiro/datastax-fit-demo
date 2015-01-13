$ cd ~/IdeaProjects/bdp && cp ../dse-demo/target/dse-demo-1.0-SNAPSHOT.jar resources/solr/lib/ && cd -

insert into solr (id, json) values (1, '{"city":"brasilia","state":"df", "country":"brasil"}');


insert into solr (id, json) values (2, '{"city":"goiania","state":"go", "country":"brasil"}');


insert into solr (id, json) values (3, '{"city":"rio de janeiro","state":"rj", "country":"brasil"}');

$ export BLOG_PATH=/Users/edward/IdeaProjects/dse-demo/src/main/resources
$ ./create-schema.sh -t $BLOG_PATH/create-table.cql -x $BLOG_PATH/schema.xml -r $BLOG_PATH/solrconfig.xml -k blog.solr

create table post (id int primary key, post blob);


create table post (id int primary key, post text);

insert into post (id, post) values (5, textAsBlob('hello'));

insert into post (id, post) values (88, 0xcafe);


cd IdeaProjects/dse-demo/ && mvn package

cd ~/IdeaProjects/bdp && cp ../dse-demo/target/dse-demo-1.0-SNAPSHOT.jar resources/solr/lib/ && cd -


cd ~/IdeaProjects/bdp && cp ../dse-demo/target/dse-demo-1.0-SNAPSHOT.jar resources/solr/lib/


curl http://localhost:8983/solr/resource/blog.solr/solrconfig.xml --data-binary @solrconfig-json.xml -H 'Content-type:text/xml; charset=utf-8'

curl http://localhost:8983/solr/resource/blog.solr/schema.xml --data-binary @schema-json.xml -H 'Content-type:text/xml; charset=utf-8'
curl -X POST "http://localhost:8983/solr/admin/cores?action=CREATE&name=blog.solr"


curl http://localhost:8983/solr/resource/blog.post/solrconfig.xml --data-binary @solrconfig-avro.xml -H 'Content-type:text/xml; charset=utf-8'

curl http://localhost:8983/solr/resource/blog.post/schema.xml --data-binary @schema-avro.xml -H 'Content-type:text/xml; charset=utf-8'

curl -X POST "http://localhost:8983/solr/admin/cores?action=CREATE&name=blog.post"