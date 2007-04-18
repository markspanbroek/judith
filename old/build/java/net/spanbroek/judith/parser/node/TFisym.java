/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import net.spanbroek.judith.parser.analysis.*;

public final class TFisym extends Token
{
    public TFisym()
    {
        super.setText("fi");
    }

    public TFisym(int line, int pos)
    {
        super.setText("fi");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TFisym(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFisym(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TFisym text.");
    }
}
