/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class AMethodcallStatement extends PStatement
{
    private PMethodcall _methodcall_;

    public AMethodcallStatement()
    {
    }

    public AMethodcallStatement(
        PMethodcall _methodcall_)
    {
        setMethodcall(_methodcall_);

    }
    public Object clone()
    {
        return new AMethodcallStatement(
            (PMethodcall) cloneNode(_methodcall_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodcallStatement(this);
    }

    public PMethodcall getMethodcall()
    {
        return _methodcall_;
    }

    public void setMethodcall(PMethodcall node)
    {
        if(_methodcall_ != null)
        {
            _methodcall_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _methodcall_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_methodcall_);
    }

    void removeChild(Node child)
    {
        if(_methodcall_ == child)
        {
            _methodcall_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_methodcall_ == oldChild)
        {
            setMethodcall((PMethodcall) newChild);
            return;
        }

    }
}
