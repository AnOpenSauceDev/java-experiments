# java-experiments

A collection of small projects I'm working on. Not always up-to-date, and currently only features an edge-art generator.

## requirements
- OpenCV as an environment variable (i.e somewhere in your path)
- enough willpower to deal with random errors
- A decent amount of ram (> 2GB) and cpu power when dealing with 1080p+ videos. (a 3b+ with swap or a pi 4 should be more than up to the task.)

And yes, it runs [Bad Apple](https://github.com/AnOpenSauceDev/java-experiments/tree/master/src/main/resources/demos)

files will be output to a `test` folder, that you must create before running,
and it's a good idea to have plenty of disk space for all the raw, uncompressed PNG's that will be rendered.

TODO's:

- [ ] optimize, possibly multithreaded.
- [ ] make everything have a config of some sort.
- [ ] make outputs look "cleaner", especially with sobel.
