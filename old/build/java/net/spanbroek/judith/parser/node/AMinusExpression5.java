/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class AMinusExpression5 extends PExpression5
{
    private TMinus _minus_;
    private PExpression5 _expression5_;

    public AMinusExpression5()
    {
    }

    public AMinusExpression5(
        TMinus _minus_,
        PExpression5 _expression5_)
    {
        setMinus(_minus_);

        setExpression5(_expression5_);

    }
    public Object clone()
    {
        return new AMinusExpression5(
            (TMinus) cloneNode(_minus_),
            (PExpression5) cloneNode(_expression5_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMinusExpression5(this);
    }

    public TMinus getMinus()
    {
        return _minus_;
    }

    public void setMinus(TMinus node)
    {
        if(_minus_ != null)
        {
            _minus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _minus_ = node;
    }

    public PExpression5 getExpression5()
    {
        return _expression5_;
    }

    public void setExpression5(PExpression5 node)
    {
        if(_expression5_ != null)
        {
            _expression5_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _expression5_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_minus_)
            + toString(_expression5_);
    }

    void removeChild(Node child)
    {
        if(_minus_ == child)
        {
            _minus_ = null;
            return;
        }

        if(_expression5_ == child)
        {
            _expression5_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_minus_ == oldChild)
        {
            setMinus((TMinus) newChild);
            return;
        }

        if(_expression5_ == oldChild)
        {
            setExpression5((PExpression5) newChild);
            return;
        }

    }
}
