/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class AEqualsExpression1 extends PExpression1
{
    private PExpression1 _expression1_;
    private TEquals _equals_;
    private PExpression2 _expression2_;

    public AEqualsExpression1()
    {
    }

    public AEqualsExpression1(
        PExpression1 _expression1_,
        TEquals _equals_,
        PExpression2 _expression2_)
    {
        setExpression1(_expression1_);

        setEquals(_equals_);

        setExpression2(_expression2_);

    }
    public Object clone()
    {
        return new AEqualsExpression1(
            (PExpression1) cloneNode(_expression1_),
            (TEquals) cloneNode(_equals_),
            (PExpression2) cloneNode(_expression2_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEqualsExpression1(this);
    }

    public PExpression1 getExpression1()
    {
        return _expression1_;
    }

    public void setExpression1(PExpression1 node)
    {
        if(_expression1_ != null)
        {
            _expression1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _expression1_ = node;
    }

    public TEquals getEquals()
    {
        return _equals_;
    }

    public void setEquals(TEquals node)
    {
        if(_equals_ != null)
        {
            _equals_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _equals_ = node;
    }

    public PExpression2 getExpression2()
    {
        return _expression2_;
    }

    public void setExpression2(PExpression2 node)
    {
        if(_expression2_ != null)
        {
            _expression2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _expression2_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_expression1_)
            + toString(_equals_)
            + toString(_expression2_);
    }

    void removeChild(Node child)
    {
        if(_expression1_ == child)
        {
            _expression1_ = null;
            return;
        }

        if(_equals_ == child)
        {
            _equals_ = null;
            return;
        }

        if(_expression2_ == child)
        {
            _expression2_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_expression1_ == oldChild)
        {
            setExpression1((PExpression1) newChild);
            return;
        }

        if(_equals_ == oldChild)
        {
            setEquals((TEquals) newChild);
            return;
        }

        if(_expression2_ == oldChild)
        {
            setExpression2((PExpression2) newChild);
            return;
        }

    }
}
