/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import net.spanbroek.judith.parser.analysis.*;

public final class TOpenbrace extends Token
{
    public TOpenbrace()
    {
        super.setText("(");
    }

    public TOpenbrace(int line, int pos)
    {
        super.setText("(");
        setLine(line);
        setPos(pos);
    }

    public Object clone()
    {
      return new TOpenbrace(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTOpenbrace(this);
    }

    public void setText(String text)
    {
        throw new RuntimeException("Cannot change TOpenbrace text.");
    }
}
