/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class AMultipleStatements extends PStatements
{
    private PStatements _statements_;
    private PStatement _statement_;

    public AMultipleStatements()
    {
    }

    public AMultipleStatements(
        PStatements _statements_,
        PStatement _statement_)
    {
        setStatements(_statements_);

        setStatement(_statement_);

    }
    public Object clone()
    {
        return new AMultipleStatements(
            (PStatements) cloneNode(_statements_),
            (PStatement) cloneNode(_statement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMultipleStatements(this);
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

    public PStatement getStatement()
    {
        return _statement_;
    }

    public void setStatement(PStatement node)
    {
        if(_statement_ != null)
        {
            _statement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _statement_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_statements_)
            + toString(_statement_);
    }

    void removeChild(Node child)
    {
        if(_statements_ == child)
        {
            _statements_ = null;
            return;
        }

        if(_statement_ == child)
        {
            _statement_ = null;
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

        if(_statement_ == oldChild)
        {
            setStatement((PStatement) newChild);
            return;
        }

    }
}
