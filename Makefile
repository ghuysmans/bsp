SAMPLES=300
all: ana scenes/ellipses-r.pdf

.SECONDARY:
%.csv: %.txt
	./gendata.sh $< ${SAMPLES} >$@

%.pdf: %.csv
	./analysis.r $<
	mv Rplots.pdf $@
%-f.pdf: %.csv
	./analysis.r -e First $<
	mv Rplots.pdf $@

%-r.pdf: $(wildcard scenes/%*.csv)
	./linreg.r $<
	mv Rplots.pdf $@

ANA=scenes/ellipsesLarge-f.pdf scenes/randomHuge-f.pdf
ANA=scenes/ellipsesLarge.pdf scenes/randomHuge.pdf
#don't use analysis here otherwise it'll try compiling some Rational Fortran...
ana: ${ANA}
