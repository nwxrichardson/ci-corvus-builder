FROM eclipse-temurin:21

COPY ./docker-entrypoint.sh ./docker-entrypoint.sh
RUN dos2unix ./docker-entrypoint.sh && chmod +x ./docker-entrypoint.sh

COPY ./products/uk.ac.york.ci.corvus.builder.product/target/products ./test

RUN cd test \
&& tar -xvzf uk.ac.york.ci.corvus.builder.product-linux.gtk.x86_64.tar.gz

RUN cd test/corvus.builder.product_1.0.0 && ls \
&& chmod +x eclipse

ENTRYPOINT [ "/docker-entrypoint.sh" ]

