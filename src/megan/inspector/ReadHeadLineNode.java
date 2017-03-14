/*
 *  Copyright (C) 2017 Daniel H. Huson
 *
 *  (Some files contain contributions from other authors, who are then mentioned separately.)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package megan.inspector;

import megan.data.IReadBlock;

/**
 * node to represent the head line for a read
 * Daniel Huson, 2.2006
 */
public class ReadHeadLineNode extends NodeBase {
    private final IReadBlock readBlock;

    /**
     * constructor
     * @param readBlock
     */
    public ReadHeadLineNode(IReadBlock readBlock) {
        super(readBlock.getReadName());
        this.readBlock = readBlock;
        this.rank = readBlock.getNumberOfMatches();
    }

    public boolean isLeaf() {
        return readBlock == null; // never leaf, because at least data node is contained below
    }

    public String toString() {
        if (getReadLength() <= 0) {
            if (rank <= 0)
                return getName();
            else
                return getName() + " [matches=" + (int) rank + "]";
        } else // readLength>0
        {
            if (rank <= 0)
                return getName() + " [length=" + getReadLength() + "]";
            else
                return getName() + " [length=" + getReadLength() + ", matches=" + (int) rank + "]";

        }
    }

    public long getUId() {
        return readBlock.getUId();
    }

    public int getReadLength() {
        return readBlock.getReadLength();
    }

    public IReadBlock getReadBlock() {
        return readBlock;
    }
}
