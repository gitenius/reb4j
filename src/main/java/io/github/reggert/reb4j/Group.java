package io.github.reggert.reb4j;

import fj.data.LazyString;

/**
 * Expression that has been grouped in parentheses.
 */
public final class Group extends AbstractQuantifiableSequenceableAlternative 
	implements Quantifiable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * The expression that is enclosed in parentheses.
	 */
	public final Expression nested;
	
	private final LazyString expression;

	private Group(final Expression nested, final LazyString opening)
	{
		if (nested == null) throw new NullPointerException("nested");
		assert opening != null;
		this.nested = nested;
		this.expression = opening.append(nested.expression()).append(")");
	}
	
	@Override
	public LazyString expression()
	{
		return expression;
	}
	
	/**
	 * Constructs a capturing group.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group capture(final Expression nested)
	{
		return new Group(nested, LazyString.str("("));
	}
	
	/**
	 * Constructs a non-capturing group.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group nonCapturing(final Expression nested)
	{
		return new Group(nested, LazyString.str("(?:"));
	}
	
	/**
	 * Constructs an independent group.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group independent(final Expression nested) 
	{
		return new Group(nested, LazyString.str("(?>"));
	}
	
	/**
	 * Constructs a group that uses positive look-ahead.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group positiveLookAhead(final Expression nested) 
	{
		return new Group(nested, LazyString.str("(?="));
	}
	
	/**
	 * Constructs a group that uses negative look-ahead.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group negativeLookAhead(final Expression nested) 
	{
		return new Group(nested, LazyString.str("(?!"));
	}
	
	/**
	 * Constructs a group that uses positive look-behind.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group positiveLookBehind(final Expression nested) 
	{
		return new Group(nested, LazyString.str("(?<="));
	}
	
	/**
	 * Constructs a group that uses negative look-behind.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group negativeLookBehind(final Expression nested) 
	{
		return new Group(nested, LazyString.str("(?<!"));
	}
	
	/**
	 * Constructs a group that enables the specified matcher flags.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @param flags
	 *  the flags to enable.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group enableFlags(final Expression nested, final Flag... flags) 
	{
		return new Group(nested, LazyString.str("(?").append(Flag.toString(flags)).append(":"));
	}
	
	/**
	 * Constructs a group that disables the specified matcher flags.
	 * 
	 * @param nested
	 * 	the expression to enclose; must not be <code>null</code>.
	 * @param flags
	 *  the flags to disable.
	 * @return a new Group.
	 * @throws NullPointerException
	 * 	if <var>nested</var> is <code>null</code>.
	 */
	public static Group disableFlags(final Expression nested, final Flag... flags) 
	{
		return new Group(nested, LazyString.str("(?-").append(Flag.toString(flags)).append(":"));
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode();
		result = prime * result + nested.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Group other = (Group) obj;
		return expression.equals(other.expression) && nested.equals(other.nested);
	}

}
