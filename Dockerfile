FROM eclipse-temurin:21

RUN apt-get update && apt-get install -y --no-install-recommends \
  bzr \
  cvs \
  git \
  mercurial \
  subversion \
  dos2unix \
  libxtst-dev \
  xvfb \
 libgtk-4-1 \ 
  && rm -rf /var/lib/apt/lists/*

COPY ./docker-entrypoint.sh ./docker-entrypoint.sh
RUN dos2unix ./docker-entrypoint.sh && chmod +x ./docker-entrypoint.sh

COPY ./products/uk.ac.york.ci.corvus.product/target/products ./test

RUN cd test \
&& tar -xvzf uk.ac.york.ci.corvus.product-linux.gtk.x86_64.tar.gz

RUN cd test/corvus.product_1.0.0 && ls \
&& chmod +x eclipse

ENTRYPOINT [ "/docker-entrypoint.sh" ]

