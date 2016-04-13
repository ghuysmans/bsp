SAMPLES=300
all: ana syn

.SECONDARY:
%.csv: %.txt
	./gendata.sh $< ${SAMPLES} >$@

%.pdf: %.csv
	./analysis.r $<
	mv Rplots.pdf $@
%-f.pdf: %.csv
	./analysis.r -e First $<
	mv Rplots.pdf $@

%-s.pdf:
	./synthesis.r $(dir $@) $(notdir $(basename $@))
	mv Rplots.pdf $@

ANA=scenes/ellipsesLarge-f.pdf scenes/randomHuge-f.pdf
ANA=scenes/ellipsesLarge.pdf scenes/randomHuge.pdf
#don't use analysis here otherwise it'll try compiling some Rational Fortran...
ana: ${ANA}

SYN=scenes/ellipsesLarge.csv scenes/ellipsesMedium.csv scenes/ellipsesSmall.csv
SYN=scenes/randomLarge.csv scenes/randomMedium.csv scenes/randomSmall.csv
SYN+=scenes/ellipses-s.pdf scenes/random-s.pdf
syn: ${SYN}
