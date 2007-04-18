/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class ALanguage extends PLanguage
{
    private PStatements _statements_;

    public ALanguage()
    {
    }

    public ALanguage(
        PStatements _statements_)
    {
        setStatements(_statements_);

    }
    public Object clone()
    {
        return new ALanguage(
            (PStatements) cloneNode(_statements_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALanguage(this);
    }

    public PStatements getStatements()
    {
        return _statements_;
    }

    public void setStatements(PStatements node)
    {
        if(_statements_ != null)
        {
            _statements_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _statements_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_statements_);
    }

    void removeChild(Node child)
    {
        if(_statements_ == child)
        {
            _statements_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_statements_ == oldChild)
        {
            setStatements((PStatements) newChild);
            return;
        }

    }
}
