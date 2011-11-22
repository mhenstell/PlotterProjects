export PATH=$PATH:/opt/local/bin:/usr/local/bin
env
cat ~/Dropbox/Projects/PlotterProjects/PlotterWebcam/capture.png | pngtopnm | pnmquant 4 | pamchannel 2 | pamtopnm -a | pnmtojpeg > ~/Dropbox/Projects/PlotterProjects/PlotterWebcam/captureChan2.jpg;
cat ~/Dropbox/Projects/PlotterProjects/PlotterWebcam/captureChan2.jpg | jpegtopnm | mkbitmap -f 2 -s 2 -t 0.47 | potrace -t 8 --color=#000000 -s -o ~/Dropbox/Projects/PlotterProjects/PlotterWebcam/captureChan2.svg;
