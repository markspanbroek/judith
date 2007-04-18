/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.spanbroek.judith.parser.node;

import java.util.*;
import net.spanbroek.judith.parser.analysis.*;

public final class ASimpleMethod extends PMethod
{
    private TMethodsym _methodsym_;
    private TIdentifier _identifier_;
    private TOpenbracket _openbracket_;
    private PStatements _statements_;
    private TClosebracket _closebracket_;

    public ASimpleMethod()
    {
    }

    public ASimpleMethod(
        TMethodsym _methodsym_,
        TIdentifier _identifier_,
        TOpenbracket _openbracket_,
        PStatements _statements_,
        TClosebracket _closebracket_)
    {
        setMethodsym(_methodsym_);

        setIdentifier(_identifier_);

        setOpenbracket(_openbracket_);

        setStatements(_statements_);

        setClosebracket(_closebracket_);

    }
    public Object clone()
    {
        return new ASimpleMethod(
            (TMethodsym) cloneNode(_methodsym_),
            (TIdentifier) cloneNode(_identifier_),
            (TOpenbracket) cloneNode(_openbracket_),
            (PStatements) cloneNode(_statements_),
            (TClosebracket) cloneNode(_closebracket_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleMethod(this);
    }

    public TMethodsym getMethodsym()
    {
        return _methodsym_;
    }

    public void setMethodsym(TMethodsym node)
    {
        if(_methodsym_ != null)
        {
            _methodsym_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _methodsym_ = node;
    }

    public TIdentifier getIdentifier()
    {
        return _identifier_;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(_identifier_ != null)
        {
            _identifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _identifier_ = node;
    }

    public TOpenbracket getOpenbracket()
    {
        return _openbracket_;
    }

    public void setOpenbracket(TOpenbracket node)
    {
        if(_openbracket_ != null)
        {
            _openbracket_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _openbracket_ = node;
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

    public TClosebracket getClosebracket()
    {
        return _closebracket_;
    }

    public void setClosebracket(TClosebracket node)
    {
        if(_closebracket_ != null)
        {
            _closebracket_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _closebracket_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_methodsym_)
            + toString(_identifier_)
            + toString(_openbracket_)
            + toString(_statements_)
            + toString(_closebracket_);
    }

    void removeChild(Node child)
    {
        if(_methodsym_ == child)
        {
            _methodsym_ = null;
            return;
        }

        if(_identifier_ == child)
        {
            _identifier_ = null;
            return;
        }

        if(_openbracket_ == child)
        {
            _openbracket_ = null;
            return;
        }

        if(_statements_ == child)
        {
            _statements_ = null;
            return;
        }

        if(_closebracket_ == child)
        {
            _closebracket_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_methodsym_ == oldChild)
        {
            setMethodsym((TMethodsym) newChild);
            return;
        }

        if(_identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        if(_openbracket_ == oldChild)
        {
            setOpenbracket((TOpenbracket) newChild);
            return;
        }

        if(_statements_ == oldChild)
        {
            setStatements((PStatements) newChild);
            return;
        }

        if(_closebracket_ == oldChild)
        {
            setClosebracket((TClosebracket) newChild);
            return;
        }

    }
}
