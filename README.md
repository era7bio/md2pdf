# md2pdf

A wrapper around pandoc for producing PDFs from markdown files; it relies on one of our [pandoc templates](https://github.com/era7bio/.pandoc) for doing it.

## install ##

This is a [conscript](http://github.com/n8han/conscript) app, so

``` bash
# is that easy:
cs era7bio/md2pdf
```

**important** `md2pdf` assumes that you have a xelatex `default.tex` template under your pandoc datadir. _If_ you use our [pandoc datadir](https://github.com/era7bio/.pandoc), you're all set :)

## use

just type `md2pdf` and you'll see a usage message.