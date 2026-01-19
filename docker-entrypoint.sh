#!/bin/bash

Xvfb :100 -ac &
export DISPLAY=:100

cd /test/corvus.product_1.0.0
./eclipse

exit 0
