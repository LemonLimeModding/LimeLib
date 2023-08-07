package tech.lemonlime.lib.multiblock.old;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.state.property.Property;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public class LongProperty extends Property<Long> {
    private final ImmutableSet<Long> values;
    private final long min;
    private final long max;

    protected LongProperty(String name, long min, long max) {
        super(name, Long.class);
        if (min < 0) {
            throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
        } else if (max <= min) {
            throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
        } else {
            this.min = min;
            this.max = max;
            Set<Long> set = Sets.newHashSet();

            for(long i = min; i <= max; ++i) {
                set.add(i);
            }

            this.values = ImmutableSet.copyOf(set);
        }
    }

    public Collection<Long> getValues() {
        return this.values;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof LongProperty longProperty && super.equals(object)) {
            return this.values.equals(longProperty.values);
        } else {
            return false;
        }
    }

    public int computeHashCode() {
        return (31 * super.computeHashCode() + this.values.hashCode());
    }

    public static LongProperty of(String name, long min, long max) {
        return new LongProperty(name, min, max);
    }

    public Optional<Long> parse(String name) {
        try {
            long Lvalue = Long.parseLong(name);
            return Lvalue >= this.min && Lvalue <= this.max ? Optional.of(Lvalue) : Optional.empty();
        } catch (NumberFormatException var3) {
            return Optional.empty();
        }
    }

    public String name(Long Lvalue) {
        return Lvalue.toString();
    }
}
