/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class ABooleanLiteral extends PLiteral
{
    private TBoolean _boolean_;

    public ABooleanLiteral()
    {
    }

    public ABooleanLiteral(
        TBoolean _boolean_)
    {
        setBoolean(_boolean_);

    }
    public Object clone()
    {
        return new ABooleanLiteral(
            (TBoolean) cloneNode(_boolean_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABooleanLiteral(this);
    }

    public TBoolean getBoolean()
    {
        return _boolean_;
    }

    public void setBoolean(TBoolean node)
    {
        if(_boolean_ != null)
        {
            _boolean_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _boolean_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_boolean_);
    }

    void removeChild(Node child)
    {
        if(_boolean_ == child)
        {
            _boolean_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_boolean_ == oldChild)
        {
            setBoolean((TBoolean) newChild);
            return;
        }

    }
}
