package ibxm;

public class Channel {
    public static final int NEAREST = 0;
    public static final int LINEAR = 1;
    public static final int SINC = 2;
    private static final int[] periodTable = new int[] {
            29021, 28812, 28605, 28399, 28195, 27992, 27790, 27590, 27392, 27195, 26999, 26805, 26612, 26421, 26231,
            26042, 25855, 25669, 25484, 25301, 25119, 24938, 24758, 24580, 24403, 24228, 24054, 23881, 23709, 23538,
            23369, 23201, 23034, 22868, 22704, 22540, 22378, 22217, 22057, 21899, 21741, 21585, 21429, 21275, 21122,
            20970, 20819, 20670, 20521, 20373, 20227, 20081, 19937, 19793, 19651, 19509, 19369, 19230, 19091, 18954,
            18818, 18682, 18548, 18414, 18282, 18150, 18020, 17890, 17762, 17634, 17507, 17381, 17256, 17132, 17008,
            16886, 16765, 16644, 16524, 16405, 16287, 16170, 16054, 15938, 15824, 15710, 15597, 15485, 15373, 15263,
            15153, 15044, 14936, 14828, 14721, 14616, 14510, 14406, 14302, 14199, 14097, 13996, 13895, 13795, 13696,
            13597, 13500, 13403, 13306, 13210, 13115, 13021, 12927, 12834, 12742, 12650, 12559, 12469, 12379, 12290,
            12202, 12114, 12027, 11940, 11854, 11769, 11684, 11600
    };
    private static final int[] freqTable = new int[] {
            267616, 269555, 271509, 273476, 275458, 277454, 279464, 281489, 283529, 285584, 287653, 289738, 291837,
            293952, 296082, 298228, 300389, 302566, 304758, 306966, 309191, 311431, 313688, 315961, 318251, 320557,
            322880, 325220, 327576, 329950, 332341, 334749, 337175, 339618, 342079, 344558, 347055, 349570, 352103,
            354655, 357225, 359813, 362420, 365047, 367692, 370356, 373040, 375743, 378466, 381209, 383971, 386754,
            389556, 392379, 395222, 398086, 400971, 403877, 406803, 409751, 412720, 415711, 418723, 421758, 424814,
            427892, 430993, 434116, 437262, 440430, 443622, 446837, 450075, 453336, 456621, 459930, 463263, 466620,
            470001, 473407, 476838, 480293, 483773, 487279, 490810, 494367, 497949, 501557, 505192, 508853, 512540,
            516254, 519995, 523763, 527558, 531381, 535232, 539111, 543017, 546952, 550915, 554908, 558929, 562979
    };
    private static final short[] sineTable = new short[] {
            0, 24, 49, 74, 97, 120, 141, 161, 180, 197, 212, 224, 235, 244, 250, 253, 255, 253, 250, 244, 235, 224, 212,
            197, 180, 161, 141, 120, 97, 74, 49, 24
    };
    private final Module module;
    private final GlobalVol globalVol;
    private Instrument instrument;
    private Sample sample;
    private boolean keyOn;
    private int noteKey;
    private int noteIns;
    private int noteVol;
    private int noteEffect;
    private int noteParam;
    private int sampleOffset;
    private int sampleIdx;
    private int sampleFra;
    private int freq;
    private int ampl;
    private int volume;
    private int panning;
    private int fadeOutVol;
    private int volEnvTick;
    private int panEnvTick;
    private int period;
    private int portaPeriod;
    private int retrigCount;
    private int fxCount;
    private int autoVibratoCount;
    private int portaUpParam;
    private int portaDownParam;
    private int tonePortaParam;
    private int offsetParam;
    private int finePortaUpParam;
    private int finePortaDownParam;
    private int extraFinePortaParam;
    private int arpeggioParam;
    private int vslideParam;
    private int globalVslideParam;
    private int panningSlideParam;
    private int fineVslideUpParam;
    private int fineVslideDownParam;
    private int retrigVolume;
    private int retrigTicks;
    private int tremorOnTicks;
    private int tremorOffTicks;
    private int vibratoType;
    private int vibratoPhase;
    private int vibratoSpeed;
    private int vibratoDepth;
    private int tremoloType;
    private int tremoloPhase;
    private int tremoloSpeed;
    private int tremoloDepth;
    private int tremoloAdd;
    private int vibratoAdd;
    private int arpeggioAdd;
    private int randomSeed;
    public int plRow;

    public Channel(final Module module, final int id, final GlobalVol globalVol) {
        this.module = module;
        this.globalVol = globalVol;
        panning = module.defaultPanning[id];
        instrument = new Instrument();
        sample = instrument.samples[0];
        randomSeed = (id + 1) * 11259375;
    }

    public void resample(final int[] outBuf, final int offset, final int length, final int sampleRate, final int interpolation) {
        if (ampl > 0) {
            final int lAmpl = ampl * 255 >> 8;
            final int rAmpl = ampl * 255 >> 8;
            final int step = (freq << 12) / (sampleRate >> 3);
            switch (interpolation) {
                case 0:
                    sample.resampleNearest(sampleIdx, sampleFra, step, lAmpl, rAmpl, outBuf, offset, length);
                    break;
                case 1:
                default:
                    sample.resampleLinear(sampleIdx, sampleFra, step, lAmpl, rAmpl, outBuf, offset, length);
                    break;
                case 2:
                    sample.resampleSinc(sampleIdx, sampleFra, step, lAmpl, rAmpl, outBuf, offset, length);
            }

        }
    }

    public void updateSampleIdx(final int length, final int sampleRate) {
        final int step = (freq << 12) / (sampleRate >> 3);
        sampleFra += step * length;
        sampleIdx = sample.normaliseSampleIdx(sampleIdx + (sampleFra >> 15));
        sampleFra &= 32767;
    }

    public void row(final Note note) {
        noteKey = note.key;
        noteIns = note.instrument;
        noteVol = note.volume;
        noteEffect = note.effect;
        noteParam = note.param;
        ++retrigCount;
        vibratoAdd = tremoloAdd = arpeggioAdd = fxCount = 0;
        if (noteEffect != 125 && noteEffect != 253 || noteParam <= 0) {
            trigger();
        }

        label157: switch (noteEffect) {
            case 1:
            case 134:
                if (noteParam > 0) {
                    portaUpParam = noteParam;
                }

                portamentoUp(portaUpParam);
                break;
            case 2:
            case 133:
                if (noteParam > 0) {
                    portaDownParam = noteParam;
                }

                portamentoDown(portaDownParam);
                break;
            case 3:
            case 135:
                if (noteParam > 0) {
                    tonePortaParam = noteParam;
                }
                break;
            case 4:
            case 136:
                if (noteParam >> 4 > 0) {
                    vibratoSpeed = noteParam >> 4;
                }

                if ((noteParam & 15) > 0) {
                    vibratoDepth = noteParam & 15;
                }

                vibrato(false);
                break;
            case 5:
            case 140:
                if (noteParam > 0) {
                    vslideParam = noteParam;
                }

                volumeSlide();
                break;
            case 6:
            case 139:
                if (noteParam > 0) {
                    vslideParam = noteParam;
                }

                vibrato(false);
                volumeSlide();
                break;
            case 7:
            case 146:
                if (noteParam >> 4 > 0) {
                    tremoloSpeed = noteParam >> 4;
                }

                if ((noteParam & 15) > 0) {
                    tremoloDepth = noteParam & 15;
                }

                tremolo();
                break;
            case 8:
                panning = noteParam < 128 ? noteParam << 1 : 255;
                break;
            case 10:
            case 132:
                if (noteParam > 0) {
                    vslideParam = noteParam;
                }

                volumeSlide();
                break;
            case 12:
                volume = noteParam >= 64 ? 64 : noteParam & 63;
                break;
            case 16:
            case 150:
                globalVol.volume = noteParam >= 64 ? 64 : noteParam & 63;
                break;
            case 17:
                if (noteParam > 0) {
                    globalVslideParam = noteParam;
                }
                break;
            case 20:
                keyOn = false;
                break;
            case 21:
                volEnvTick = panEnvTick = noteParam & 255;
                break;
            case 25:
                if (noteParam > 0) {
                    panningSlideParam = noteParam;
                }
                break;
            case 27:
            case 145:
                if (noteParam >> 4 > 0) {
                    retrigVolume = noteParam >> 4;
                }

                if ((noteParam & 15) > 0) {
                    retrigTicks = noteParam & 15;
                }

                retrigVolSlide();
                break;
            case 29:
            case 137:
                if (noteParam >> 4 > 0) {
                    tremorOnTicks = noteParam >> 4;
                }

                if ((noteParam & 15) > 0) {
                    tremorOffTicks = noteParam & 15;
                }

                tremor();
                break;
            case 33:
                if (noteParam > 0) {
                    extraFinePortaParam = noteParam;
                }

                switch (extraFinePortaParam & 240) {
                    case 16:
                        portamentoUp(224 | extraFinePortaParam & 15);
                        break label157;
                    case 32:
                        portamentoDown(224 | extraFinePortaParam & 15);
                    default:
                        break label157;
                }
            case 113:
                if (noteParam > 0) {
                    finePortaUpParam = noteParam;
                }

                portamentoUp(240 | finePortaUpParam & 15);
                break;
            case 114:
                if (noteParam > 0) {
                    finePortaDownParam = noteParam;
                }

                portamentoDown(240 | finePortaDownParam & 15);
                break;
            case 116:
            case 243:
                if (noteParam < 8) {
                    vibratoType = noteParam;
                }
                break;
            case 119:
            case 244:
                if (noteParam < 8) {
                    tremoloType = noteParam;
                }
                break;
            case 122:
                if (noteParam > 0) {
                    fineVslideUpParam = noteParam;
                }

                volume += fineVslideUpParam;
                if (volume > 64) {
                    volume = 64;
                }
                break;
            case 123:
                if (noteParam > 0) {
                    fineVslideDownParam = noteParam;
                }

                volume -= fineVslideDownParam;
                if (volume < 0) {
                    volume = 0;
                }
                break;
            case 124:
            case 252:
                if (noteParam <= 0) {
                    volume = 0;
                }
                break;
            case 138:
                if (noteParam > 0) {
                    arpeggioParam = noteParam;
                }
                break;
            case 149:
                if (noteParam >> 4 > 0) {
                    vibratoSpeed = noteParam >> 4;
                }

                if ((noteParam & 15) > 0) {
                    vibratoDepth = noteParam & 15;
                }

                vibrato(true);
                break;
            case 248:
                panning = noteParam * 17;
        }

        autoVibrato();
        calculateFrequency();
        calculateAmplitude();
        updateEnvelopes();
    }

    public void tick() {
        vibratoAdd = 0;
        ++fxCount;
        ++retrigCount;
        if (noteEffect != 125 || fxCount > noteParam) {
            switch (noteVol & 240) {
                case 96:
                    volume -= noteVol & 15;
                    if (volume < 0) {
                        volume = 0;
                    }
                    break;
                case 112:
                    volume += noteVol & 15;
                    if (volume > 64) {
                        volume = 64;
                    }
                    break;
                case 176:
                    vibratoPhase += vibratoSpeed;
                    vibrato(false);
                    break;
                case 208:
                    panning -= noteVol & 15;
                    if (panning < 0) {
                        panning = 0;
                    }
                    break;
                case 224:
                    panning += noteVol & 15;
                    if (panning > 255) {
                        panning = 255;
                    }
                    break;
                case 240:
                    tonePortamento();
            }
        }

        switch (noteEffect) {
            case 1:
            case 134:
                portamentoUp(portaUpParam);
                break;
            case 2:
            case 133:
                portamentoDown(portaDownParam);
                break;
            case 3:
            case 135:
                tonePortamento();
                break;
            case 4:
            case 136:
                vibratoPhase += vibratoSpeed;
                vibrato(false);
                break;
            case 5:
            case 140:
                tonePortamento();
                volumeSlide();
                break;
            case 6:
            case 139:
                vibratoPhase += vibratoSpeed;
                vibrato(false);
                volumeSlide();
                break;
            case 7:
            case 146:
                tremoloPhase += tremoloSpeed;
                tremolo();
                break;
            case 10:
            case 132:
                volumeSlide();
                break;
            case 17:
                globalVol.volume += (globalVslideParam >> 4) - (globalVslideParam & 15);
                if (globalVol.volume < 0) {
                    globalVol.volume = 0;
                }

                if (globalVol.volume > 64) {
                    globalVol.volume = 64;
                }
                break;
            case 25:
                panning += (panningSlideParam >> 4) - (panningSlideParam & 15);
                if (panning < 0) {
                    panning = 0;
                }

                if (panning > 255) {
                    panning = 255;
                }
                break;
            case 27:
            case 145:
                retrigVolSlide();
                break;
            case 29:
            case 137:
                tremor();
                break;
            case 121:
                if (fxCount >= noteParam) {
                    fxCount = 0;
                    sampleIdx = sampleFra = 0;
                }
                break;
            case 124:
            case 252:
                if (noteParam == fxCount) {
                    volume = 0;
                }
                break;
            case 125:
            case 253:
                if (noteParam == fxCount) {
                    trigger();
                }
                break;
            case 138:
                if (fxCount > 2) {
                    fxCount = 0;
                }

                if (fxCount == 0) {
                    arpeggioAdd = 0;
                }

                if (fxCount == 1) {
                    arpeggioAdd = arpeggioParam >> 4;
                }

                if (fxCount == 2) {
                    arpeggioAdd = arpeggioParam & 15;
                }
                break;
            case 149:
                vibratoPhase += vibratoSpeed;
                vibrato(true);
        }

        autoVibrato();
        calculateFrequency();
        calculateAmplitude();
        updateEnvelopes();
    }

    private void updateEnvelopes() {
        if (instrument.volumeEnvelope.enabled) {
            if (!keyOn) {
                fadeOutVol -= instrument.volumeFadeOut;
                if (fadeOutVol < 0) {
                    fadeOutVol = 0;
                }
            }

            volEnvTick = instrument.volumeEnvelope.nextTick(volEnvTick, keyOn);
        }

        if (instrument.panningEnvelope.enabled) {
            panEnvTick = instrument.panningEnvelope.nextTick(panEnvTick, keyOn);
        }

    }

    private void autoVibrato() {
        int depth = instrument.vibratoDepth & 127;
        if (depth > 0) {
            final int sweep = instrument.vibratoSweep & 127;
            final int rate = instrument.vibratoRate & 127;
            final int type = instrument.vibratoType;
            if (autoVibratoCount < sweep) {
                depth = depth * autoVibratoCount / sweep;
            }

            vibratoAdd += waveform(autoVibratoCount * rate >> 2, type + 4) * depth >> 8;
            ++autoVibratoCount;
        }

    }

    private void volumeSlide() {
        final int up = vslideParam >> 4;
        final int down = vslideParam & 15;
        if (down == 15 && up > 0) {
            if (fxCount == 0) {
                volume += up;
            }
        } else if (up == 15 && down > 0) {
            if (fxCount == 0) {
                volume -= down;
            }
        } else if (fxCount > 0 || module.fastVolSlides) {
            volume += up - down;
        }

        if (volume > 64) {
            volume = 64;
        }

        if (volume < 0) {
            volume = 0;
        }

    }

    private void portamentoUp(final int param) {
        switch (param & 240) {
            case 224:
                if (fxCount == 0) {
                    period -= param & 15;
                }
                break;
            case 240:
                if (fxCount == 0) {
                    period -= (param & 15) << 2;
                }
                break;
            default:
                if (fxCount > 0) {
                    period -= param << 2;
                }
        }

        if (period < 0) {
            period = 0;
        }

    }

    private void portamentoDown(final int param) {
        if (period > 0) {
            switch (param & 240) {
                case 224:
                    if (fxCount == 0) {
                        period += param & 15;
                    }
                    break;
                case 240:
                    if (fxCount == 0) {
                        period += (param & 15) << 2;
                    }
                    break;
                default:
                    if (fxCount > 0) {
                        period += param << 2;
                    }
            }

            if (period > '\uffff') {
                period = '\uffff';
            }
        }

    }

    private void tonePortamento() {
        if (period > 0) {
            if (period < portaPeriod) {
                period += tonePortaParam << 2;
                if (period > portaPeriod) {
                    period = portaPeriod;
                }
            } else {
                period -= tonePortaParam << 2;
                if (period < portaPeriod) {
                    period = portaPeriod;
                }
            }
        }

    }

    private void vibrato(final boolean fine) {
        vibratoAdd = waveform(vibratoPhase, vibratoType & 3) * vibratoDepth >> (fine ? 7 : 5);
    }

    private void tremolo() {
        tremoloAdd = waveform(tremoloPhase, tremoloType & 3) * tremoloDepth >> 6;
    }

    private int waveform(final int phase, final int type) {
        final boolean amplitude = false;
        int amplitude1;
        switch (type) {
            case 1:
            case 7:
                amplitude1 = 255 - ((phase + 32 & 63) << 3);
                break;
            case 2:
            case 5:
                amplitude1 = (phase & 32) > 0 ? 255 : -255;
                break;
            case 3:
            case 8:
                amplitude1 = (randomSeed >> 20) - 255;
                randomSeed = randomSeed * 65 + 17 & 536870911;
                break;
            case 4:
            default:
                amplitude1 = sineTable[phase & 31];
                if ((phase & 32) > 0) {
                    amplitude1 = -amplitude1;
                }
                break;
            case 6:
                amplitude1 = ((phase + 32 & 63) << 3) - 255;
        }

        return amplitude1;
    }

    private void tremor() {
        if (retrigCount >= tremorOnTicks) {
            tremoloAdd = -64;
        }

        if (retrigCount >= tremorOnTicks + tremorOffTicks) {
            tremoloAdd = retrigCount = 0;
        }

    }

    private void retrigVolSlide() {
        if (retrigCount >= retrigTicks) {
            retrigCount = sampleIdx = sampleFra = 0;
            switch (retrigVolume) {
                case 1:
                    --volume;
                    break;
                case 2:
                    volume -= 2;
                    break;
                case 3:
                    volume -= 4;
                    break;
                case 4:
                    volume -= 8;
                    break;
                case 5:
                    volume -= 16;
                    break;
                case 6:
                    volume -= volume / 3;
                    break;
                case 7:
                    volume >>= 1;
                case 8:
                default:
                    break;
                case 9:
                    ++volume;
                    break;
                case 10:
                    volume += 2;
                    break;
                case 11:
                    volume += 4;
                    break;
                case 12:
                    volume += 8;
                    break;
                case 13:
                    volume += 16;
                    break;
                case 14:
                    volume += volume >> 1;
                    break;
                case 15:
                    volume <<= 1;
            }

            if (volume < 0) {
                volume = 0;
            }

            if (volume > 64) {
                volume = 64;
            }
        }

    }

    private void calculateFrequency() {
        int per;
        if (module.linearPeriods) {
            per = period + vibratoAdd - (arpeggioAdd << 6);
            if (per < 28 || per > 7680) {
                per = 7680;
            }

            final int tone = 7680 - per;
            final int i = (tone >> 3) % 96;
            final int c = freqTable[i];
            final int m = freqTable[i + 1] - c;
            final int x = tone & 7;
            final int y = (m * x >> 3) + c;
            freq = y >> 9 - tone / 768;
        } else {
            per = period + vibratoAdd;
            per = per * (periodTable[(arpeggioAdd & 15) << 3] << 1) / periodTable[0];
            per = (per >> 1) + (per & 1);
            if (per < 28) {
                per = periodTable[0];
            }

            freq = module.c2Rate * 1712 / per;
        }

    }

    private void calculateAmplitude() {
        int envVol = keyOn ? 64 : 0;
        if (instrument.volumeEnvelope.enabled) {
            envVol = instrument.volumeEnvelope.calculateAmpl(volEnvTick);
        }

        int vol = volume + tremoloAdd;
        if (vol > 64) {
            vol = 64;
        }

        if (vol < 0) {
            vol = 0;
        }

        vol = vol * module.gain * '耀' >> 13;
        vol = vol * fadeOutVol >> 15;
        ampl = vol * globalVol.volume * envVol >> 12;
        int envPan = 32;
        if (instrument.panningEnvelope.enabled) {
            envPan = instrument.panningEnvelope.calculateAmpl(panEnvTick);
        }

        final int panRange = panning < 128 ? panning : 255 - panning;
    }

    private void trigger() {
        if (noteIns > 0 && noteIns <= module.numInstruments) {
            instrument = module.instruments[noteIns];
            final Sample isPorta = instrument.samples[instrument.keyToSample[noteKey < 97 ? noteKey : 0]];
            volume = isPorta.volume >= 64 ? 64 : isPorta.volume & 63;
            if (isPorta.panning >= 0) {
                panning = isPorta.panning & 255;
            }

            if (period > 0 && isPorta.looped()) {
                sample = isPorta;
            }

            sampleOffset = volEnvTick = panEnvTick = 0;
            fadeOutVol = '耀';
            keyOn = true;
        }

        if (noteEffect == 9 || noteEffect == 143) {
            if (noteParam > 0) {
                offsetParam = noteParam;
            }

            sampleOffset = offsetParam << 8;
        }

        if (noteVol >= 16 && noteVol < 96) {
            volume = noteVol < 80 ? noteVol - 16 : 64;
        }

        switch (noteVol & 240) {
            case 128:
                volume -= noteVol & 15;
                if (volume < 0) {
                    volume = 0;
                }
                break;
            case 144:
                volume += noteVol & 15;
                if (volume > 64) {
                    volume = 64;
                }
                break;
            case 160:
                if ((noteVol & 15) > 0) {
                    vibratoSpeed = noteVol & 15;
                }
                break;
            case 176:
                if ((noteVol & 15) > 0) {
                    vibratoDepth = noteVol & 15;
                }

                vibrato(false);
                break;
            case 192:
                panning = (noteVol & 15) * 17;
                break;
            case 240:
                if ((noteVol & 15) > 0) {
                    tonePortaParam = noteVol & 15;
                }
        }

        if (noteKey > 0) {
            if (noteKey > 96) {
                keyOn = false;
            } else {
                final boolean isPorta1 = (noteVol & 240) == 240 || noteEffect == 3 || noteEffect == 5 || noteEffect == 135 || noteEffect == 140;
                if (!isPorta1) {
                    sample = instrument.samples[instrument.keyToSample[noteKey]];
                }

                int fineTune = sample.fineTune;
                if (noteEffect == 117 || noteEffect == 242) {
                    fineTune = (noteParam & 15) << 4;
                    if (fineTune > 127) {
                        fineTune -= 256;
                    }
                }

                int key = noteKey + sample.relNote;
                if (key < 1) {
                    key = 1;
                }

                if (key > 120) {
                    key = 120;
                }

                int per = keyToPeriod(key, fineTune, module.linearPeriods);
                per = module.c2Rate * per * 2 / sample.c2Rate;
                portaPeriod = (per >> 1) + (per & 1);
                if (!isPorta1) {
                    period = portaPeriod;
                    sampleIdx = sampleOffset;
                    sampleFra = 0;
                    if (vibratoType < 4) {
                        vibratoPhase = 0;
                    }

                    if (tremoloType < 4) {
                        tremoloPhase = 0;
                    }

                    retrigCount = autoVibratoCount = 0;
                }
            }
        }

    }

    public static int keyToPeriod(final int key, final int fineTune, final boolean linear) {
        if (linear)
            return 7744 - (key << 6) - (fineTune >> 1);
        else {
            final int tone = (key << 6) + (fineTune >> 1);
            final int i = (tone >> 3) % 96;
            final int c = periodTable[i] * 2;
            final int m = periodTable[i + 1] * 2 - c;
            final int x = tone & 7;
            final int y = (m * x >> 3) + c >> tone / 768;
            return (y >> 1) + (y & 1);
        }
    }

    public static int periodToKey(int period) {
        int key = 0;

        int oct;
        for (oct = 0; period < periodTable[96]; ++oct) {
            period <<= 1;
        }

        while (key < 12) {
            final int d1 = periodTable[key << 3] - period;
            final int d2 = period - periodTable[key + 1 << 3];
            if (d2 >= 0) {
                if (d2 < d1) {
                    ++key;
                }
                break;
            }

            ++key;
        }

        return oct * 12 + key;
    }
}
